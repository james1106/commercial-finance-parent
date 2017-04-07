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
	"github.com/xncc/util"
)

// SimpleChaincode example simple Chaincode implementation
type SimpleChaincode struct {
}

// cc deploy 执行方法
func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("########### ChainCode Init ###########")
	_, _, err := util.GetFunctionAndParameters(stub.GetArgs())
	if err != nil {
		return util.Response(util.CODE_PARAM_ERROR, err.Error(), nil)
	}

	return util.Response(util.CODE_OK, "OK", nil)
}

// cc 执行操作统一入口
func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("########### ChainCode Invoke ###########")

	function, args, err := util.GetFunctionAndParameters(stub.GetArgs())
	if err != nil {
		return util.Response(util.CODE_PARAM_ERROR, err.Error(), nil)
	}

	if function != "invoke" {
		return util.Response(util.CODE_UNKNOWN_FUNCTION, "Unknown function call", nil)
	}

	action, params, err := util.GetActionAndParameters(args)
	if err != nil {
		return util.Response(util.CODE_UNKNOWN_FUNCTION, err.Error(), nil)
	}

	// 请求参数转换为对象
	fmt.Println(params)

	switch action {
	// 执行合约
	case "excute":
		return t.excute(stub, params[0])
	case "update":
		// TODO
		return nil
	// 添加流程审批记录
	case "addCheckLog":
		return util.Response(util.CODE_OK, "OK", nil)
	// 查询合约
	case "query":
		return t.query(stub, params[0])
	default:
		return util.Response(util.CODE_UNKNOWN_FUNCTION, "未知操作", nil)
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
	if err := proto.Unmarshal(data, request); err != nil {
		return util.Response(util.CODE_PROTOBUF_DATA_PARSE, "请求参数错误", nil)
	}

	action := request.GetAction()

	switch action {
	case "init":
		return t.init(stub, request.GetContractData())
	case "check":
		return t.check(stub, request.GetNo(), request.GetContractData())
	default:
		return util.Response(util.CODE_UNKNOWN_FUNCTION, "未知操作", nil)
	}

}

// 初始化合约
func (t *SimpleChaincode) init(stub shim.ChaincodeStubInterface, data []byte) pb.Response {
	// 将数据解码用于数据操作
	request := &contract.FinancingContract{}
	if err := proto.Unmarshal(data, request); err != nil {
		return util.Response(util.CODE_PROTOBUF_DATA_PARSE, "请求参数错误", nil)
	}

	fmt.Printf("合约数据初始化编号: %s", request.OrderNo)
	// 数据编码存储
	out, err := proto.Marshal(request)
	if err != nil {
		return util.Response(util.CODE_PROTOBUF_DATA_ENCODE, "合约数据序列化保存错误:"+err.Error(), nil)
	}
	err = stub.PutState(request.OrderNo, out)
	if err != nil {
		return util.Response(util.CODE_SERVER_ERROR, "合约数据保存错误:"+err.Error(), nil)
	}
	return util.Response(util.CODE_OK, "OK", nil)
}

// 合约审核
// 审核成功将会流转到下一个状态
func (t *SimpleChaincode) check(stub shim.ChaincodeStubInterface, key string, data []byte) pb.Response {
	// 将数据解码用于数据操作

	fmt.Printf("合约数据编号: %s\n", key)
	order, result := t.getContract(stub, key)
	if result.Code != 0 {
		return util.ResponseOut(result)
	}

	// 获取合约的当前阶段
	currentStep := order.GetContractStatus().GetCurrentStep()
	//currentStep.Org

	// 校验用户
	if err := t.valiCreator(stub, currentStep.GetOrg()); err != nil {
		return util.Response(util.CODE_UNAUTHORIZED, "用户未授权:"+err.Error(), nil)
	}

	// 获取合约的下一阶段
	nextStep, err := t.getNextStep(stub, currentStep)
	if err != nil {
		return util.Response(util.CODE_SERVER_ERROR, err.Error(), nil)
	}
	order.ContractStatus.CurrentStep = nextStep

	// 新增阶段历史记录
	stepHistory := order.GetContractStatus().GetStepHistory()
	stepHistorySlice := stepHistory[:]
	stepHistorySlice = append(stepHistorySlice, currentStep)
	order.ContractStatus.StepHistory = stepHistorySlice

	// 数据编码存储
	out, err := proto.Marshal(order)
	if err != nil {
		return util.Response(util.CODE_PROTOBUF_DATA_ENCODE, "合约数据序列化保存错误:"+err.Error(), nil)
	}
	err = stub.PutState(key, out)
	if err != nil {
		return util.Response(util.CODE_SERVER_ERROR, "合约数据保存错误:"+err.Error(), nil)
	}
	return util.Response(util.CODE_OK, "OK", nil)
}

// 获取当前阶段的下一阶段
func (t *SimpleChaincode) getNextStep(stub shim.ChaincodeStubInterface, currentStep *configuration.ContractStep) (*configuration.ContractStep, error) {

	ledgerFlowData := t.getLedger(stub, "", "获取合约流程配置")
	if ledgerFlowData.Code != 0 {
		return nil, errors.New(ledgerFlowData.Message)
	}

	contractFlow := &configuration.ContractFlow{}
	if err := proto.Unmarshal(ledgerFlowData.Result, contractFlow); err != nil {
		return nil, errors.New("获取合约流程配置反序列化错误")
	}

	var nextStep *configuration.ContractStep

	steps := contractFlow.GetSteps()
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
	if err := proto.Unmarshal(data, request); err != nil {
		return util.Response(util.CODE_PROTOBUF_DATA_PARSE, "请求参数格式错误:"+err.Error(), nil)
	}

	contractData, result := t.getContract(stub, request.GetNo())
	if result.Code != 0 {
		return util.ResponseOut(result)
	}
	switch request.GetType() {
	case contract.ContractQueryRequest_ALL:
		contractData = t.getAll(contractData)
		out, err := proto.Marshal(result)
		if err != nil {
			return shim.Error("组装返回结果数据序列化成protobuf数据错误:" + err.Error())
		}
		return util.Response(util.CODE_OK, "OK", out)

	case contract.ContractQueryRequest_FILE:
		file := t.getFile(contractData, request.GetFileKey())
		out, err := proto.Marshal(file)
		if err != nil {
			return shim.Error("组装返回结果数据序列化成protobuf数据错误:" + err.Error())
		}
		return util.Response(util.CODE_OK, "OK", out)
	default:
		return util.Response(util.CODE_PROTOBUF_DATA_PARSE, "请求参数错误:", nil)
	}

}

// 返回所有合约字段数据
func (t *SimpleChaincode) getAll(order *contract.FinancingContract) *contract.FinancingContract {
	// 移除文件数据,只保留文件key
	// 点开查看具体文件时在查询具体文件
	files := order.GetContractData().GetOrderFiles().GetFiles()
	for key := range files {
		files[key] = nil
	}
	order.ContractData.OrderFiles.Files = files
	return order
}

// 返回具体的文件数据
func (t *SimpleChaincode) getFile(order *contract.FinancingContract, fileKey string) *common.LedgerFile {
	// 移除文件数据,只保留文件key
	// 点开查看具体文件时在查询具体文件
	files := order.GetContractData().GetOrderFiles().GetFiles()
	for key, file := range files {
		if key == fileKey {
			return file
		}
	}
	return nil
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
		result.Code = util.CODE_PROTOBUF_DATA_PARSE
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
			Code:    util.CODE_SERVER_ERROR,
			Message: baseMessage + ":获取数据错误:" + err.Error(),
		}
	}

	if data == nil {
		return &common.ChainCodeResponse{
			Code:    util.CODE_DATA_NOT_FOUND,
			Message: baseMessage + "：数据不存在",
		}
	}

	return &common.ChainCodeResponse{
		Code:    util.CODE_OK,
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
