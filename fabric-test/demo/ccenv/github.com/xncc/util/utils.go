package util

import (
	"errors"
	"fmt"
	"github.com/golang/protobuf/proto"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	pb "github.com/hyperledger/fabric/protos/peer"
	"github.com/xncc/protos/common"
)

type ResponseCode struct {
	code    int32
	message string
}

// 状态编码定义:
const (
	CODE_OK                   = 0    // 成功
	CODE_UNAUTHORIZED         = 999  // 用户未授权
	CODE_PARAM_ERROR          = 1000 // 参数格式错误
	CODE_UNKNOWN_FUNCTION     = 1001 // 未知方法错误
	CODE_PROTOBUF_DATA_PARSE  = 1002 // protobuf数据转换成对象错误
	CODE_PROTOBUF_DATA_ENCODE = 1003 // protobuf数据序列化编码错误
	CODE_DATA_NOT_FOUND       = 4004 // 数据不存在错误
	CODE_SERVER_ERROR         = 5000 // cc服务执行错误或未知错误
)

// 从原始参数中获得请求方法和请求参数
// 请求方法:init、invoke
func GetFunctionAndParameters(args [][]byte) (function string, params [][]byte, err error) {
	if len(args) > 1 {
		function = string(args[0])
		params = args[1:]
	} else {
		err = errors.New("Incorrect number of arguments. Expecting at least 1")
	}
	return
}

func GetActionAndParameters(args [][]byte) (action string, params [][]byte, err error) {
	if len(args) > 1 {
		action = string(args[0])
		params = args[1:]
	} else {
		err = errors.New("Incorrect number of arguments. Expecting at least 1")
	}
	return
}

// 返回数据封装（返回结果需要统一格式）
func Response(code int32, message string, data []byte) pb.Response {
	result := &common.ChainCodeResponse{
		Code:    code,
		Message: message,
		Result:  data,
	}

	return ResponseOut(result)
}

// 返回数据封装（返回结果需要统一格式）
func ResponseOut(result *common.ChainCodeResponse) pb.Response {

	fmt.Println("返回结果:")
	fmt.Printf("code = %d message = %s \n", result.Code, result.Message)
	fmt.Println(result.String())

	out, err := proto.Marshal(result)
	if err != nil {
		return shim.Error("组装返回结果数据序列化成protobuf数据错误:" + err.Error())
	}

	return shim.Success(out)
}
