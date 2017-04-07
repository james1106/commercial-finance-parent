package ledgerfile

import (
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"github.com/xncc/protos/common"
	"github.com/xncc/util/ccutil"
)

// 查询指定文件的文件流
func GetFileToView(stub shim.ChaincodeStubInterface, key string) *common.ChainCodeResponse {
	fmt.Printf("文件流查询key=%s \n", key)
	data, err := stub.GetState("LEDGERFILE_" + key)
	if err != nil {
		return &common.ChainCodeResponse{
			Code:    ccutil.CODE_SERVER_ERROR,
			Message: "文件流查询-获取数据错误:" + err.Error(),
		}
	}

	if data == nil {
		return &common.ChainCodeResponse{
			Code:    ccutil.CODE_DATA_NOT_FOUND,
			Message: "文件流查询-数据不存在",
		}
	}

	return &common.ChainCodeResponse{
		Code:    ccutil.CODE_OK,
		Message: "OK",
		Result:  data,
	}
}

// 保存指定文件的文件流
func SaveFileData(stub shim.ChaincodeStubInterface, key string, data []byte) *common.ChainCodeResponse {
	fmt.Printf("保存指定文件的文件流key=%s \n", key)
	if len(key) == 0 || data == nil {
		return &common.ChainCodeResponse{
			Code:    ccutil.CODE_PARAM_ERROR,
			Message: "参数错误",
		}
	}
	err := stub.PutState("LEDGERFILE_"+key, data)
	if err != nil {
		return &common.ChainCodeResponse{
			Code:    ccutil.CODE_SERVER_ERROR,
			Message: "保存指定文件的文件流错误:" + err.Error(),
		}
	}

	return &common.ChainCodeResponse{
		Code:    ccutil.CODE_OK,
		Message: "OK",
	}
}
