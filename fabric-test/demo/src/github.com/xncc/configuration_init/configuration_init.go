package main

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"

	"errors"
	"github.com/golang/protobuf/proto"
	"github.com/hyperledger/commercial-finance/fabric-test/demo/src/github.com/xncc/protos/common"
	"log"
)

// SimpleChaincode example simple Chaincode implementation
type SimpleChaincode struct {
}

func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("########### ChainCode Init ###########")
	_, _, err := getFunctionAndParameters(stub.GetArgs())
	if err != nil {
		return response(1000, err.Error(), nil)
	}
	// TODO

	return response(0, "OK", nil)

}

func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("########### ChainCode Invoke ###########")
	function, args, err := getFunctionAndParameters(stub.GetArgs())
	if err != nil {
		return response(1000, err.Error(), nil)
	}

	if function != "invoke" {
		return response(1001, "Unknown function call", nil)
	}

	action, params,err := getActionAndParameters(args)
	if err != nil {
		return response(1001, "Unknown function call", nil)
	}

	if action == "init" {
		if len(params) != 4 {
			return shim.Error("init func Incorrect number of arguments. Expecting 4")
		}
		return t.init(stub, string(params[0]), params[1])
	}

	if action == "query" {
		if len(params) != 3 {
			return shim.Error("query func Incorrect number of arguments. Expecting 3")
		}
		return t.query(stub, string(params[0]))
	}

	return shim.Error("Unknown action, check the first argument, must be one of 'delete', 'query', or 'move'")
}

// 从原始参数中获得请求方法和请求参数
// 请求方法:init、invoke
func getFunctionAndParameters(args [][]byte) (function string, params [][]byte, err error) {
	if len(args) > 1 {
		function = string(args[0])
		params = args[1:]
	} else {
		err = errors.New("Incorrect number of arguments. Expecting at least 1")
	}
	return
}

func getActionAndParameters(args [][]byte) (action string, params [][]byte, err error) {
	if len(args) > 1 {
		action = string(args[0])
		params = args[1:]
	} else {
		err = errors.New("Incorrect number of arguments. Expecting at least 1")
	}
	return
}

// 返回数据封装（返回结果需要统一格式）
func response(code int32, message string, data []byte) pb.Response {
	// 组装方法返回数据
	result := &common.ChainCodeResponse{
		Code:    code,
		Message: message,
		Result:  data,
	}
	out, err := proto.Marshal(result)
	if err != nil {
		log.Fatalln("Failed to encode address book:", err)
	}

	return shim.Success(out)
}

func (t *SimpleChaincode) init(stub shim.ChaincodeStubInterface, key string, data []byte) pb.Response {
	fmt.Printf("key = %s", key)
	for _, barg := range data {
		fmt.Printf("%d , ", barg)
	}
	fmt.Println("--------------------------")
	// 将数据解码用于数据操作

	// 数据操作

	// 数据编码存储

	return shim.Success(nil)
}

func (t *SimpleChaincode) query(stub shim.ChaincodeStubInterface, key string) pb.Response {
	fmt.Printf("query data key = %s", key)
	data, err := stub.GetState(key)
	if err != nil {
		jsonResp := "{\"Error\":\"Failed to get state for " + key + "\"}"
		log.Fatalln("Failed to get state for "+key+":", err)
		return shim.Error(jsonResp)
	}

	fmt.Println("--------------------------")
	for _, barg := range data {
		fmt.Printf("%d , ", barg)
	}
	fmt.Println("--------------------------")
	//// 将数据解码用于数据操作
	//order := &orderpb.OrderExample{}
	//err = proto.Unmarshal(data, order)
	//if err != nil {
	//	log.Fatalln("Failed to parse address book:", err)
	//	return shim.Error(fmt.Errorf("Failed to parse Order: %s", err).Error())
	//}
	//fmt.Printf("KEY = %s, Data = %s\n", key, order.String())

	return shim.Success(data)
}

func main() {
	err := shim.Start(new(SimpleChaincode))
	if err != nil {
		fmt.Printf("Error starting Simple chaincode: %s", err)
	}
}
