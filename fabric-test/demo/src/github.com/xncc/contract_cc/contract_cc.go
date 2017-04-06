package main

import (
	"fmt"
	"github.com/golang/protobuf/proto"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
	"github.com/xncc/protos/configuration"
	"github.com/xncc/protos/contract"
	ccutil "github.com/xncc/util"
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

	// 校验用户
	if err := t.valiCreator(stub); err != nil {
		return ccutil.Response(ccutil.CODE_UNAUTHORIZED, "用户未授权:"+err.Error(), nil)
	}

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

	// 请求参数转换为对象
	fmt.Println(params)

	switch action {
	// 执行合约
	case "excute":
		return t.excute(stub, params[0])
	// 添加流程审批记录
	case "addCheckLog":
		return ccutil.Response(ccutil.CODE_OK, "OK", nil)
	// 查询合约
	case "query":
		return t.query(stub, params[0])
	default:
		return ccutil.Response(ccutil.CODE_UNKNOWN_FUNCTION, "未知操作", nil)
	}
}

// 校验用户
func (t *SimpleChaincode) valiCreator(stub shim.ChaincodeStubInterface) error {
	// 获取creator
	//creator, err := stub.GetCreator()
	//if err != nil {
	//	fmt.Errorf("get creator error : %s", err.Error())
	//}
	//
	//fmt.Printf("creator:%s \n", string(creator))
	//
	//var tcert *x509.Certificate
	//tcert, err = utils.DERToX509Certificate(creator)
	//if err != nil {
	//	fmt.Errorf("format creator error : %s", err.Error())
	//}
	//
	//fmt.Printf("keyid: %s \n", string(tcert.AuthorityKeyId))
	return nil
}

// 执行合约操作
// 操作包括:初始申请，申请状态流转
func (t *SimpleChaincode) excute(stub shim.ChaincodeStubInterface, data []byte) pb.Response {
	request := &contract.ContractExcuteRequest{}
	if err := proto.Unmarshal(data, request); err != nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "请求参数错误", nil)
	}

	action := request.GetAction()

	switch action {
	case "init":
		return t.init(stub, request.GetContractData())
	case "check":
		return t.check(stub, request.GetNo(), request.GetContractData())
	default:
		return ccutil.Response(ccutil.CODE_UNKNOWN_FUNCTION, "未知操作", nil)
	}

}

// 初始化合约
func (t *SimpleChaincode) init(stub shim.ChaincodeStubInterface, data []byte) pb.Response {
	// 将数据解码用于数据操作
	request := &contract.FinancingContract{}
	if err := proto.Unmarshal(data, request); err != nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "请求参数错误", nil)
	}

	fmt.Printf("合约数据编号: %s", request.OrderNo)
	// 初始数据校验
	// TODO
	// 数据编码存储
	out, err := proto.Marshal(request)
	if err != nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_ENCODE, "合约数据序列化保存错误:"+err.Error(), nil)
	}
	err = stub.PutState(request.OrderNo, out)
	if err != nil {
		return ccutil.Response(ccutil.CODE_SERVER_ERROR, "合约数据保存错误:"+err.Error(), nil)
	}
	return ccutil.Response(ccutil.CODE_OK, "OK", nil)
}

// 合约审核
// 审核成功将会流转到下一个状态
func (t *SimpleChaincode) check(stub shim.ChaincodeStubInterface, key string, data []byte) pb.Response {
	// 将数据解码用于数据操作

	fmt.Printf("合约数据编号: %s\n", key)
	data, err := stub.GetState(key)
	if err != nil {
		return ccutil.Response(ccutil.CODE_SERVER_ERROR, "获取合约数据错误:"+err.Error(), nil)
	}

	order := &contract.FinancingContract{}
	if err := proto.Unmarshal(data, order); err != nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约数据反序列化错误:"+err.Error(), nil)
	}

	// 获取合约的当前阶段
	var currentStep *configuration.ContractStep
	currentStep = order.GetContractStatus().GetCurrentStep()
	fmt.Printf("合约当前阶段:%s\n", &currentStep.String())

	// 获取三方账本初始的流程配置
	stub.GetState("")
	// 获取合约的下一阶段
	// 数据校验
	// TODO
	// 数据编码存储
	out, err := proto.Marshal(order)
	if err != nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_ENCODE, "合约数据序列化保存错误:"+err.Error(), nil)
	}
	err = stub.PutState(key, out)
	if err != nil {
		return ccutil.Response(ccutil.CODE_SERVER_ERROR, "合约数据保存错误:"+err.Error(), nil)
	}
	return ccutil.Response(ccutil.CODE_OK, "OK", nil)
}

// 合约数据查询
func (t *SimpleChaincode) query(stub shim.ChaincodeStubInterface, data []byte) pb.Response {
	request := &contract.ContractQueryRequest{}
	if err := proto.Unmarshal(data, request); err != nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "请求参数格式错误:"+err.Error(), nil)
	}

	key := request.GetNo()
	fmt.Printf("合约数据编号: %s\n", key)

	// 查询合约数据
	data, err := stub.GetState(key)
	if err != nil {
		return ccutil.Response(ccutil.CODE_SERVER_ERROR, "获取合约数据错误:"+err.Error(), nil)
	}

	// 反序列化合约数据进行操作处理
	order := &contract.FinancingContract{}
	if err := proto.Unmarshal(data, order); err != nil {
		return ccutil.Response(ccutil.CODE_PROTOBUF_DATA_PARSE, "合约数据反序列化错误:"+err.Error(), nil)
	}

	// TODO 对合约进行相关逻辑处理

	return ccutil.Response(ccutil.CODE_OK, "OK", data)
}

func main() {
	fmt.Println("启动合约流程chaincode")
	err := shim.Start(new(SimpleChaincode))
	if err != nil {
		fmt.Printf("启动合约流程失败: %s", err)
	}
}
