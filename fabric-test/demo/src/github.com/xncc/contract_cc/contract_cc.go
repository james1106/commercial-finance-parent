package main

import (
	"errors"
	"fmt"
	"github.com/golang/protobuf/proto"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
	"github.com/xncc/protos/common"
	"github.com/xncc/protos/configuration"
	"github.com/xncc/protos/contract"
	"github.com/xncc/util/ccutil"
	"github.com/xncc/util/ledgerfile"
)

// SimpleChaincode example simple Chaincode implementation
type SimpleChaincode struct {
}

// cc deploy 执行方法
func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("########### ChainCode Init ###########")
	_, _, err := ccutil.GetFunctionAndParameters(stub.GetArgs())
	if err != nil {
		return ccutil.Response(ccutil.CODE_PARAM_ERROR, err.Error(), nil)
	}

	return ccutil.Response(ccutil.CODE_OK, "OK", nil)
}

// cc 执行操作统一入口
func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("########### ChainCode Invoke ###########")

	function, args, err := ccutil.GetFunctionAndParameters(stub.GetArgs())
	if err != nil {
		return ccutil.Response(ccutil.CODE_PARAM_ERROR, err.Error(), nil)
	}

	if function != "invoke" {
		return ccutil.Response(ccutil.CODE_UNKNOWN_FUNCTION, "Unknown function call", nil)
	}

	action, params, err := ccutil.GetActionAndParameters(args)
	if err != nil {
		return ccutil.Response(ccutil.CODE_UNKNOWN_FUNCTION, err.Error(), nil)
	}

	switch action {
	// 执行合约
	case "excute":
		return t.excute(stub, params[0])
	// 查询合约
	case "query":
		return t.query(stub, params[0])
	default:
		return ccutil.Response(ccutil.CODE_UNKNOWN_FUNCTION, "未知操作", nil)
	}
}

func main() {
	fmt.Println("启动合约流程chaincode")
	err := shim.Start(new(SimpleChaincode))
	if err != nil {
		fmt.Printf("启动合约流程失败: %s", err)
	}
}

// 执行合约操作
// 操作包括:初始申请，申请状态流转
func (t *SimpleChaincode) excute(stub shim.ChaincodeStubInterface, data []byte) pb.Response {
	request := &contract.ContractExcuteRequest{}
	if err := proto.Unmarshal(data, request); err != nil || request == nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "请求参数错误", nil)
	}

	switch request.Action {
	case "init":
		return t.init(stub, request)
	case "check":
		return t.check(stub, request)
	case "excute":
		return t.excuteContract(stub, request)
	default:
		return ccutil.Response(ccutil.CODE_UNKNOWN_FUNCTION, "未知操作", nil)
	}

}

// 初始化合约
func (t *SimpleChaincode) init(stub shim.ChaincodeStubInterface, request *contract.ContractExcuteRequest) pb.Response {
	fmt.Printf("合约数据初始化 key=%s NO=%s \n", request.ContractKey, request.ContractNO)
	order := &contract.FinancingContract{}
	order.OrderNo = request.ContractNO

	// 将数据解码用于数据操作
	formData := &contract.ContractFormData{}
	if err := proto.Unmarshal(request.Playload[0], formData); err != nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约数据格式错误", nil)
	}
	order.ContractData = formData
	fmt.Printf("合约数据: %s", formData.String())
	fmt.Println(len(request.Playload[1]))
	// 发票数据
	if len(request.Playload[1]) > 1 {
		invoic := &contract.InvoiceAddRequest{}
		if err := proto.Unmarshal(request.Playload[1], invoic); err != nil {
			return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约发票数据格式错误", nil)
		}
		order.Invoices = invoic.Invoice
	}
	fmt.Println(len(request.Playload[2]))
	// 附件处理
	if len(request.Playload[2]) > 1 {
		fileRequest := &contract.FileAddRequest{}
		if err := proto.Unmarshal(request.Playload[2], fileRequest); err != nil {
			return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约附件数据格式错误", nil)
		}

		order.Files = fileRequest.FileInfos

		fileDatas := fileRequest.FileDatas
		if fileDatas != nil {
			for _, fileData := range fileDatas {
				ledgerfile.SaveFileData(stub, fileData.Key, fileData.Data)
			}
		}
	}

	// 操作数据
	if request.Playload[3] != nil {
		operateInfo := &common.OperateInfo{}
		if err := proto.Unmarshal(request.Playload[3], operateInfo); err != nil {
			return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约操作信息数据格式错误", nil)
		}
		operateInfos := []*common.OperateInfo{}
		operateInfos = append(operateInfos, operateInfo)
		order.OperateInfo = operateInfos
	}

	// 合约状态(初始)
	status := &contract.ContractStatus{}
	status.Status = contract.StatusType_CREATE
	steps, err := t.getContractSteps(stub)
	if err != nil {
		return ccutil.Response(ccutil.CODE_SERVER_ERROR, "查询合约流程配置错误:"+err.Error(), nil)
	}
	status.CurrentStep = steps[0]
	order.ContractStatus = status

	fmt.Printf("初始化合约数据: %s \n", order.String())

	// 数据编码存储
	out, err := proto.Marshal(order)
	if err != nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_ENCODE, "合约数据序列化保存错误:"+err.Error(), nil)
	}
	err = stub.PutState(request.ContractKey, out)
	if err != nil {
		return ccutil.Response(ccutil.CODE_SERVER_ERROR, "合约数据保存错误:"+err.Error(), nil)
	}
	return ccutil.Response(ccutil.CODE_OK, "OK", nil)
}

// 新增流程审批记录&确认项数据&申请数据更新
func (t *SimpleChaincode) check(stub shim.ChaincodeStubInterface, request *contract.ContractExcuteRequest) pb.Response {
	fmt.Printf("合约数据 key=%s\n", request.ContractKey)
	order, result := t.getContract(stub, request.ContractKey)
	if result.Code != 0 {
		return ccutil.ResponseOut(result)
	}

	// 合约表单数据
	if request.Playload[0] != nil {
		formData := &contract.ContractFormData{}
		if err := proto.Unmarshal(request.Playload[0], formData); err != nil {
			return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约数据格式错误", nil)
		}
		order.ContractData = formData
		fmt.Printf("合约数据: %s", formData.String())
	}

	// 发票数据
	if request.Playload[1] != nil {
		invoic := &contract.InvoiceAddRequest{}
		if err := proto.Unmarshal(request.Playload[1], invoic); err != nil {
			return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约发票数据格式错误", nil)
		}
		order.Invoices = invoic.Invoice
	}

	// 附件处理
	if request.Playload[2] != nil {
		fileRequest := &contract.FileAddRequest{}
		if err := proto.Unmarshal(request.Playload[2], fileRequest); err != nil {
			return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约附件数据格式错误", nil)
		}
		files := order.Files
		for _, fileData := range fileRequest.FileInfos {
			files = append(files, fileData)
		}
		order.Files = files

		// 文件流处理
		fileDatas := fileRequest.FileDatas
		if fileDatas != nil {
			for _, fileData := range fileDatas {
				ledgerfile.SaveFileData(stub, fileData.Key, fileData.Data)
			}
		}
	}

	// 操作数据
	if request.Playload[3] != nil {
		operateInfo := &common.OperateInfo{}
		if err := proto.Unmarshal(request.Playload[3], operateInfo); err != nil {
			return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约操作信息数据格式错误", nil)
		}
		operateInfos := order.OperateInfo
		operateInfos = append(operateInfos, operateInfo)
		order.OperateInfo = operateInfos
	}

	// 合约状态(初始)
	status := order.ContractStatus

	// 流程审批记录
	if request.Playload[4] != nil {
		checkFlows := &contract.CheckFlowRequest{}
		if err := proto.Unmarshal(request.Playload[4], checkFlows); err != nil {
			return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约流程审批记录数据格式错误", nil)
		}

		if status.CheckFlowDatas == nil {
			status.CheckFlowDatas = []*contract.ContractCheckFlowData{}
		}
		checkFlowDatas := status.CheckFlowDatas
		for _, data := range checkFlows.CheckFlows {
			checkFlowDatas = append(checkFlowDatas, data)
		}
		status.CheckFlowDatas = checkFlowDatas

	}

	// 确认项数据
	if request.Playload[5] != nil {
		checks := &contract.CheckRequest{}
		if err := proto.Unmarshal(request.Playload[5], checks); err != nil {
			return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约确认项数据格式错误", nil)
		}

		if status.CheckDatas == nil {
			status.CheckDatas = []*contract.CheckData{}
		}
		checkDatas := status.CheckDatas
		for _, data := range checks.Checks {
			checkDatas = append(checkDatas, data)
		}
		status.CheckDatas = checkDatas
	}

	order.ContractStatus = status

	//// 获取合约的当前阶段
	//currentStep := order.GetContractStatus().GetCurrentStep()
	////currentStep.Org
	//
	//// 校验用户
	//if err := t.valiCreator(stub, currentStep.GetOrg()); err != nil {
	//	return ccutil.Response(ccutil.CODE_UNAUTHORIZED, "用户未授权:"+err.Error(), nil)
	//}
	//
	//// 获取合约的下一阶段
	//nextStep, err := t.getNextStep(stub, currentStep)
	//if err != nil {
	//	return ccutil.Response(ccutil.CODE_SERVER_ERROR, err.Error(), nil)
	//}
	//order.ContractStatus.CurrentStep = nextStep
	//
	//// 新增阶段历史记录
	//stepHistory := order.GetContractStatus().GetStepHistory()
	//stepHistorySlice := stepHistory[:]
	//stepHistorySlice = append(stepHistorySlice, currentStep)
	//order.ContractStatus.StepHistory = stepHistorySlice

	// 数据编码存储
	out, err := proto.Marshal(order)
	if err != nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_ENCODE, "合约数据序列化保存错误:"+err.Error(), nil)
	}
	err = stub.PutState(request.ContractKey, out)
	if err != nil {
		return ccutil.Response(ccutil.CODE_SERVER_ERROR, "合约数据保存错误:"+err.Error(), nil)
	}
	return ccutil.Response(ccutil.CODE_OK, "OK", nil)
}

// 执行合约
// 主要是处理融资订单状态改变时，程序需要做的相应数据处理操作
// 操作包括:
// 	发起融资申请处理
//	审核确认通过处理
//	已完成放款处理
//	已完成还款处理
//	流转申请到下一处理节点
func (t *SimpleChaincode) excuteContract(stub shim.ChaincodeStubInterface, request *contract.ContractExcuteRequest) pb.Response {
	fmt.Printf("合约数据 key=%s\n", request.ContractKey)
	order, result := t.getContract(stub, request.ContractKey)
	if result.Code != 0 {
		return ccutil.ResponseOut(result)
	}
	switch request.Excute {
	case "submit":
		err := t.nextStep(stub, order, contract.StatusType_CHECK_CONFIRM)
		if err != nil {
			return ccutil.ResponseOut(&common.ChainCodeResponse{
				Code:    ccutil.CODE_SERVER_ERROR,
				Message: err.Error(),
			})
		}
		break
	case "checkPass":
		err := t.contractCheckPass(stub, order)
		if err != nil {
			return ccutil.ResponseOut(&common.ChainCodeResponse{
				Code:    ccutil.CODE_SERVER_ERROR,
				Message: err.Error(),
			})
		}
		break
	case "payLoans":
		err := t.contractPayLoans(stub, order)
		if err != nil {
			return ccutil.ResponseOut(&common.ChainCodeResponse{
				Code:    ccutil.CODE_SERVER_ERROR,
				Message: err.Error(),
			})
		}
		break
	case "payOffLoans":
		err := t.nextStep(stub, order, contract.StatusType_OVER)
		if err != nil {
			return ccutil.ResponseOut(&common.ChainCodeResponse{
				Code:    ccutil.CODE_SERVER_ERROR,
				Message: err.Error(),
			})
		}
		break
	case "next":
		err := t.nextStep(stub, order, order.ContractStatus.Status)
		if err != nil {
			return ccutil.ResponseOut(&common.ChainCodeResponse{
				Code:    ccutil.CODE_SERVER_ERROR,
				Message: err.Error(),
			})
		}
		break
	default:
		return ccutil.Response(ccutil.CODE_UNKNOWN_FUNCTION, "未知操作", nil)
	}

	// 操作数据
	if request.Playload[0] != nil {
		operateInfo := &common.OperateInfo{}
		if err := proto.Unmarshal(request.Playload[3], operateInfo); err != nil {
			return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约操作信息数据格式错误", nil)
		}
		operateInfos := order.OperateInfo
		operateInfos = append(operateInfos, operateInfo)
		order.OperateInfo = operateInfos
	}

	// 数据编码存储
	out, err := proto.Marshal(order)
	if err != nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_ENCODE, "合约数据序列化保存错误:"+err.Error(), nil)
	}
	err = stub.PutState(request.ContractKey, out)
	if err != nil {
		return ccutil.Response(ccutil.CODE_SERVER_ERROR, "合约数据保存错误:"+err.Error(), nil)
	}
	return ccutil.Response(ccutil.CODE_OK, "OK", nil)
}

// 流转申请到下一处理节点
func (t *SimpleChaincode) nextStep(stub shim.ChaincodeStubInterface, order *contract.FinancingContract, status contract.StatusType) error {
	currentStatus := order.ContractStatus
	statusHistory := order.StepHistory
	if statusHistory == nil {
		statusHistory = []*contract.ContractStatus{}
	}
	statusHistory = append(statusHistory, currentStatus)

	nextStatus := &contract.ContractStatus{}
	nextStatus.Status = status

	step, err := t.getNextStep(stub, currentStatus.CurrentStep)
	if err != nil {
		return err
	}
	nextStatus.CurrentStep = step
	order.ContractStatus = nextStatus
	return nil
}

// 审核确认通过到放款阶段
// 需要汇总处理放款金额
func (t *SimpleChaincode) contractCheckPass(stub shim.ChaincodeStubInterface, order *contract.FinancingContract) error {
	// 统计计算实际放款金额
	// TODO
	return t.nextStep(stub, order, contract.StatusType_PAY_LOAN)
}

// 已完成所有放款接下来到还款状态
// 需要汇总处理待还款金额
func (t *SimpleChaincode) contractPayLoans(stub shim.ChaincodeStubInterface, order *contract.FinancingContract) error {
	// 统计计算实际还款金额
	// TODO
	return t.nextStep(stub, order, contract.StatusType_PAYBACK_LOAN)
}

// 获取当前融资申请的流程配置
func (t *SimpleChaincode) getContractSteps(stub shim.ChaincodeStubInterface) ([]*configuration.ContractStep, error) {
	// TODO
	ledgerFlowData := t.getLedger(stub, "", "获取合约流程配置")
	if ledgerFlowData.Code != 0 {
		return nil, errors.New(ledgerFlowData.Message)
	}

	contractFlow := &configuration.ContractFlow{}
	if err := proto.Unmarshal(ledgerFlowData.Result, contractFlow); err != nil {
		return nil, errors.New("获取合约流程配置反序列化错误")
	}

	return contractFlow.GetSteps(), nil
}

// 获取当前阶段的下一阶段
func (t *SimpleChaincode) getNextStep(stub shim.ChaincodeStubInterface, currentStep *configuration.ContractStep) (*configuration.ContractStep, error) {
	var nextStep *configuration.ContractStep

	steps, err := t.getContractSteps(stub)
	if err != nil {
		return nil, err
	}

	for _, step := range steps {
		if step.StepId == currentStep.StepId {
			nextStep = step
		}
	}
	return nextStep, nil
}

// 合约查询
func (t *SimpleChaincode) query(stub shim.ChaincodeStubInterface, data []byte) pb.Response {
	request := &contract.ContractQueryRequest{}
	if err := proto.Unmarshal(data, request); err != nil || request == nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "请求参数格式错误:"+err.Error(), nil)
	}

	contractData, result := t.getContract(stub, request.ContractKey)
	if result.Code != 0 {
		return ccutil.ResponseOut(result)
	}

	fmt.Printf("合约数据: %s \n", contractData.String())

	switch request.Type {
	case contract.ContractQueryRequest_ALL:
		// 合约数据查询
		out, err := proto.Marshal(contractData)
		if err != nil {
			return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_ENCODE, "合约数据序列化错误:"+err.Error(), nil)
		}
		return ccutil.Response(ccutil.CODE_OK, "OK", out)

	case contract.ContractQueryRequest_FILE:
		// 合约相关文件具体查询
		file := t.getFile(contractData, request.FileKey, stub)
		return ccutil.ResponseOut(file)

	case contract.ContractQueryRequest_FILELILST:
		// 合约文件列表查询
		fileList := t.queryFileList(contractData)
		out, err := proto.Marshal(fileList)
		if err != nil {
			return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_ENCODE, "合约文件列表序列化错误:"+err.Error(), nil)
		}
		return ccutil.Response(ccutil.CODE_OK, "OK", out)

	default:
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "请求参数错误:", nil)
	}

}

// 返回具体的文件数据
func (t *SimpleChaincode) getFile(order *contract.FinancingContract, fileKey string, stub shim.ChaincodeStubInterface) *common.ChainCodeResponse {
	// 移除文件数据,只保留文件key
	// 点开查看具体文件时在查询具体文件
	fileList := t.queryFileList(order)
	files := fileList.Files

	if files == nil {
		return &common.ChainCodeResponse{
			Code:    ccutil.CODE_DATA_NOT_FOUND,
			Message: "合约不存在指定文件",
		}
	}

	for _, file := range files {
		if file != nil && file.Sha256 == fileKey {
			return ledgerfile.GetFileToView(stub, fileKey)
		}
	}

	return &common.ChainCodeResponse{
		Code:    ccutil.CODE_DATA_NOT_FOUND,
		Message: "合约不存在指定文件",
	}
}

// 查询合约的文件列表
func (t *SimpleChaincode) queryFileList(order *contract.FinancingContract) *common.LedgerFileList {
	result := &common.LedgerFileList{}

	if order.ContractData != nil {
		result.Files = order.Files
	}

	return result
}

// 合约数据查询
func (t *SimpleChaincode) getContract(stub shim.ChaincodeStubInterface, key string) (*contract.FinancingContract, *common.ChainCodeResponse) {
	fmt.Printf("合约数据编号: %s\n", key)

	// 查询合约数据
	result := t.getLedger(stub, key, "获取合约")
	if result.Code != 0 {
		return nil, result
	}

	// 反序列化合约数据进行操作处理
	order := &contract.FinancingContract{}
	if err := proto.Unmarshal(result.Result, order); err != nil {
		result.Code = ccutil.CODE_PROTOBUF_DATA_PARSE
		result.Message = "合约数据反序列化错误:" + err.Error()
		return nil, result
	}

	return order, result
}

// 从账本获取合约数据
func (t *SimpleChaincode) getLedger(stub shim.ChaincodeStubInterface, key string, baseMessage string) *common.ChainCodeResponse {

	fmt.Printf("从账本获取数据 key = %s\n", key)

	// 查询合约数据
	data, err := stub.GetState(key)
	if err != nil {
		return &common.ChainCodeResponse{
			Code:    ccutil.CODE_SERVER_ERROR,
			Message: baseMessage + ":获取数据错误:" + err.Error(),
		}
	}

	if data == nil {
		return &common.ChainCodeResponse{
			Code:    ccutil.CODE_DATA_NOT_FOUND,
			Message: baseMessage + "：数据不存在",
		}
	}

	return &common.ChainCodeResponse{
		Code:    ccutil.CODE_OK,
		Message: "OK",
		Result:  data,
	}
}

// 校验用户
func (t *SimpleChaincode) valiCreator(stub shim.ChaincodeStubInterface, vo *common.AppVo) error {
	//// 获取creator
	//creator, err := stub.GetCreator()
	//if err != nil{
	//	return err
	//}
	//
	//// 调用用户的私有账本cc校验
	//chaincodeName := ""
	//channel := ""
	//args := [][]byte{[]byte(vo.GetAppId()),creator}
	//result := stub.InvokeChaincode(chaincodeName,args,channel)
	//if result.Status != shim.OK {
	//	return errors.New("用户校验chaincode执行失败")
	//}
	//
	//response := &common.ChainCodeResponse{}
	//if err := proto.Unmarshal(result.Payload, response); err != nil {
	//	return errors.New("用户校验chaincode执行返回数据格式错误")
	//}
	//
	//if response.Code != CODE_OK{
	//	return errors.New("用户校验不通过")
	//}

	// TODO
	// 实现获取操作用户的证书校验是否有权限操作此数据
	return nil
}
