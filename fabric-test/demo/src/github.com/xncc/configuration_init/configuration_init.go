package main

import (
	"crypto/x509"
	"fmt"

	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"

	orderpb "github.com/example_cc/protos/order"

	"log"

	"github.com/golang/protobuf/proto"
	"github.com/hyperledger/fabric/bccsp/utils"
)

// SimpleChaincode example simple Chaincode implementation
type SimpleChaincode struct {
}

func (t *SimpleChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("########### example_cc deploy Init ###########")

	args := stub.GetArgs()
	if len(args) != 3 {
		return shim.Error("Incorrect number of arguments. Expecting 3")
	}

	function := string(args[0])
	if function != "init" {
		return shim.Error("Unknown function call")
	}

	// 获取creator
	creator, err := stub.GetCreator()
	if err != nil {
		fmt.Errorf("get creator error : %s", err.Error())
	}

	fmt.Printf("creator:%s \n", string(creator))

	var tcert *x509.Certificate
	tcert, err = utils.DERToX509Certificate(creator)
	if err != nil {
		fmt.Errorf("format creator error : %s", err.Error())
	}

	fmt.Printf("keyid: %s \n", string(tcert.AuthorityKeyId))

	fmt.Println(tcert)
	fmt.Println("--------------------------------------")

	return shim.Success(nil)

}

// Transaction makes payment of X units from A to B
func (t *SimpleChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	fmt.Println("########### example_cc Invoke ###########")
	args := stub.GetArgs()
	if len(args) < 1 {
		return shim.Error("Incorrect number of arguments. Expecting at least 1")
	}

	function := string(args[0])

	if function != "invoke" {
		return shim.Error("Unknown function call")
	}

	// 获取creator
	creator, err := stub.GetCreator()
	if err != nil {
		fmt.Errorf("get creator error : %s", err.Error())
	}
	fmt.Printf("creator:%s \n", string(creator))

	action := string(args[1])
	if action == "init" {
		if len(args) != 4 {
			return shim.Error("init func Incorrect number of arguments. Expecting 4")
		}
		return t.init(stub, string(args[2]), args[3])
	}

	if action == "query" {
		if len(args) != 3 {
			return shim.Error("query func Incorrect number of arguments. Expecting 3")
		}
		return t.query(stub, string(args[2]))
	}

	return shim.Error("Unknown action, check the first argument, must be one of 'delete', 'query', or 'move'")
}

func (t *SimpleChaincode) init(stub shim.ChaincodeStubInterface, key string, data []byte) pb.Response {
	fmt.Printf("key = %s", key)
	for _, barg := range data {
		fmt.Printf("%d , ", barg)
	}
	fmt.Println("--------------------------")
	// 将数据解码用于数据操作
	order := &orderpb.OrderExample{}
	err := proto.Unmarshal(data, order)
	if err != nil {
		log.Fatalln("Failed to parse address book:", err)
		return shim.Error(fmt.Errorf("Failed to parse Order: %s", err).Error())
	}

	// 数据操作
	fmt.Printf("KEY = %s, Data = %s\n", key, order.String())

	// 数据编码存储
	out, err := proto.Marshal(order)
	if err != nil {
		log.Fatalln("Failed to encode address book:", err)
	}
	err = stub.PutState(key, out)
	if err != nil {
		return shim.Error(err.Error())
	}

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
