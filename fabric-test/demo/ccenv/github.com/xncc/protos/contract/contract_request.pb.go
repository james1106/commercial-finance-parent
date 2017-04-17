// Code generated by protoc-gen-go.
// source: contract/contract_request.proto
// DO NOT EDIT!

package contract

import proto "github.com/golang/protobuf/proto"
import fmt "fmt"
import math "math"
import protos "github.com/xncc/protos/common"

// Reference imports to suppress errors if they are not otherwise used.
var _ = proto.Marshal
var _ = fmt.Errorf
var _ = math.Inf

// 查询类型
type ContractQueryRequest_QueryType int32

const (
	ContractQueryRequest_ALL       ContractQueryRequest_QueryType = 0
	ContractQueryRequest_FILE      ContractQueryRequest_QueryType = 1
	ContractQueryRequest_FILELILST ContractQueryRequest_QueryType = 2
)

var ContractQueryRequest_QueryType_name = map[int32]string{
	0: "ALL",
	1: "FILE",
	2: "FILELILST",
}
var ContractQueryRequest_QueryType_value = map[string]int32{
	"ALL":       0,
	"FILE":      1,
	"FILELILST": 2,
}

func (x ContractQueryRequest_QueryType) String() string {
	return proto.EnumName(ContractQueryRequest_QueryType_name, int32(x))
}
func (ContractQueryRequest_QueryType) EnumDescriptor() ([]byte, []int) {
	return fileDescriptor3, []int{6, 0}
}

// 执行合约请求参数
type ContractExcuteRequest struct {
	Action      string   `protobuf:"bytes,1,opt,name=action" json:"action,omitempty"`
	ContractKey string   `protobuf:"bytes,2,opt,name=contractKey" json:"contractKey,omitempty"`
	ContractNO  string   `protobuf:"bytes,3,opt,name=contractNO" json:"contractNO,omitempty"`
	Playload    [][]byte `protobuf:"bytes,4,rep,name=playload,proto3" json:"playload,omitempty"`
	Excute      string   `protobuf:"bytes,5,opt,name=excute" json:"excute,omitempty"`
}

func (m *ContractExcuteRequest) Reset()                    { *m = ContractExcuteRequest{} }
func (m *ContractExcuteRequest) String() string            { return proto.CompactTextString(m) }
func (*ContractExcuteRequest) ProtoMessage()               {}
func (*ContractExcuteRequest) Descriptor() ([]byte, []int) { return fileDescriptor3, []int{0} }

// 执行文件新增请求参数
type FileAddRequest struct {
	FileDatas []*protos.LedgerFileData `protobuf:"bytes,1,rep,name=fileDatas" json:"fileDatas,omitempty"`
	FileInfos []*protos.LedgerFile     `protobuf:"bytes,2,rep,name=fileInfos" json:"fileInfos,omitempty"`
}

func (m *FileAddRequest) Reset()                    { *m = FileAddRequest{} }
func (m *FileAddRequest) String() string            { return proto.CompactTextString(m) }
func (*FileAddRequest) ProtoMessage()               {}
func (*FileAddRequest) Descriptor() ([]byte, []int) { return fileDescriptor3, []int{1} }

func (m *FileAddRequest) GetFileDatas() []*protos.LedgerFileData {
	if m != nil {
		return m.FileDatas
	}
	return nil
}

func (m *FileAddRequest) GetFileInfos() []*protos.LedgerFile {
	if m != nil {
		return m.FileInfos
	}
	return nil
}

// 发票数据
type InvoiceAddRequest struct {
	Invoice []*Invoice `protobuf:"bytes,1,rep,name=invoice" json:"invoice,omitempty"`
}

func (m *InvoiceAddRequest) Reset()                    { *m = InvoiceAddRequest{} }
func (m *InvoiceAddRequest) String() string            { return proto.CompactTextString(m) }
func (*InvoiceAddRequest) ProtoMessage()               {}
func (*InvoiceAddRequest) Descriptor() ([]byte, []int) { return fileDescriptor3, []int{2} }

func (m *InvoiceAddRequest) GetInvoice() []*Invoice {
	if m != nil {
		return m.Invoice
	}
	return nil
}

// 流程审批记录
type CheckFlowRequest struct {
	CheckFlows []*ContractCheckFlowData `protobuf:"bytes,1,rep,name=checkFlows" json:"checkFlows,omitempty"`
}

func (m *CheckFlowRequest) Reset()                    { *m = CheckFlowRequest{} }
func (m *CheckFlowRequest) String() string            { return proto.CompactTextString(m) }
func (*CheckFlowRequest) ProtoMessage()               {}
func (*CheckFlowRequest) Descriptor() ([]byte, []int) { return fileDescriptor3, []int{3} }

func (m *CheckFlowRequest) GetCheckFlows() []*ContractCheckFlowData {
	if m != nil {
		return m.CheckFlows
	}
	return nil
}

// 确认检查项
type CheckRequest struct {
	Checks []*CheckData `protobuf:"bytes,1,rep,name=checks" json:"checks,omitempty"`
}

func (m *CheckRequest) Reset()                    { *m = CheckRequest{} }
func (m *CheckRequest) String() string            { return proto.CompactTextString(m) }
func (*CheckRequest) ProtoMessage()               {}
func (*CheckRequest) Descriptor() ([]byte, []int) { return fileDescriptor3, []int{4} }

func (m *CheckRequest) GetChecks() []*CheckData {
	if m != nil {
		return m.Checks
	}
	return nil
}

// 转账记录
type ContractTransactionRequest struct {
	Transactions []*ContractTransactionDetail `protobuf:"bytes,1,rep,name=transactions" json:"transactions,omitempty"`
}

func (m *ContractTransactionRequest) Reset()                    { *m = ContractTransactionRequest{} }
func (m *ContractTransactionRequest) String() string            { return proto.CompactTextString(m) }
func (*ContractTransactionRequest) ProtoMessage()               {}
func (*ContractTransactionRequest) Descriptor() ([]byte, []int) { return fileDescriptor3, []int{5} }

func (m *ContractTransactionRequest) GetTransactions() []*ContractTransactionDetail {
	if m != nil {
		return m.Transactions
	}
	return nil
}

// 合约查询请求参数
type ContractQueryRequest struct {
	ContractKey string                         `protobuf:"bytes,1,opt,name=contractKey" json:"contractKey,omitempty"`
	Type        ContractQueryRequest_QueryType `protobuf:"varint,2,opt,name=type,enum=protos.ContractQueryRequest_QueryType" json:"type,omitempty"`
	FileKey     string                         `protobuf:"bytes,3,opt,name=fileKey" json:"fileKey,omitempty"`
}

func (m *ContractQueryRequest) Reset()                    { *m = ContractQueryRequest{} }
func (m *ContractQueryRequest) String() string            { return proto.CompactTextString(m) }
func (*ContractQueryRequest) ProtoMessage()               {}
func (*ContractQueryRequest) Descriptor() ([]byte, []int) { return fileDescriptor3, []int{6} }

func init() {
	proto.RegisterType((*ContractExcuteRequest)(nil), "protos.ContractExcuteRequest")
	proto.RegisterType((*FileAddRequest)(nil), "protos.FileAddRequest")
	proto.RegisterType((*InvoiceAddRequest)(nil), "protos.InvoiceAddRequest")
	proto.RegisterType((*CheckFlowRequest)(nil), "protos.CheckFlowRequest")
	proto.RegisterType((*CheckRequest)(nil), "protos.CheckRequest")
	proto.RegisterType((*ContractTransactionRequest)(nil), "protos.ContractTransactionRequest")
	proto.RegisterType((*ContractQueryRequest)(nil), "protos.ContractQueryRequest")
	proto.RegisterEnum("protos.ContractQueryRequest_QueryType", ContractQueryRequest_QueryType_name, ContractQueryRequest_QueryType_value)
}

func init() { proto.RegisterFile("contract/contract_request.proto", fileDescriptor3) }

var fileDescriptor3 = []byte{
	// 492 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x09, 0x6e, 0x88, 0x02, 0xff, 0x64, 0x53, 0xdd, 0x8e, 0xd2, 0x40,
	0x14, 0xb6, 0x80, 0xb0, 0x9c, 0xc5, 0x95, 0x1d, 0x75, 0x6d, 0x88, 0xee, 0x62, 0x2f, 0x0c, 0x5e,
	0x58, 0xcc, 0xea, 0x8d, 0x26, 0x9a, 0xec, 0x0f, 0x24, 0xc4, 0x46, 0xb3, 0x95, 0x2b, 0x6f, 0xcc,
	0xec, 0x74, 0x16, 0x1a, 0xcb, 0x0c, 0xb6, 0x53, 0xa1, 0x0f, 0xe4, 0x63, 0xf8, 0x6e, 0xa6, 0x33,
	0x3d, 0xa5, 0xd2, 0xab, 0xf6, 0x9c, 0xef, 0xe7, 0x7c, 0x93, 0x39, 0x03, 0x67, 0x4c, 0x0a, 0x15,
	0x53, 0xa6, 0xc6, 0xf8, 0xf3, 0x23, 0xe6, 0xbf, 0x52, 0x9e, 0x28, 0x77, 0x1d, 0x4b, 0x25, 0x49,
	0x5b, 0x7f, 0x92, 0xc1, 0x23, 0x26, 0x57, 0x2b, 0x29, 0xc6, 0xe6, 0x63, 0xc0, 0xc1, 0xd3, 0x9a,
	0xba, 0x00, 0x4e, 0xeb, 0xb6, 0x89, 0xa2, 0x2a, 0x4d, 0x0a, 0xfc, 0x59, 0x1d, 0x0f, 0xa8, 0xa2,
	0x06, 0x75, 0xfe, 0x58, 0xf0, 0xe4, 0xaa, 0xe8, 0x4f, 0xb6, 0x2c, 0x55, 0xdc, 0x37, 0x99, 0xc8,
	0x09, 0xb4, 0x29, 0x53, 0xa1, 0x14, 0xb6, 0x35, 0xb4, 0x46, 0x5d, 0xbf, 0xa8, 0xc8, 0x10, 0x0e,
	0xd1, 0xe8, 0x33, 0xcf, 0xec, 0x86, 0x06, 0xab, 0x2d, 0x72, 0x0a, 0x80, 0xe5, 0x97, 0xaf, 0x76,
	0x53, 0x13, 0x2a, 0x1d, 0x32, 0x80, 0x83, 0x75, 0x44, 0xb3, 0x48, 0xd2, 0xc0, 0x6e, 0x0d, 0x9b,
	0xa3, 0x9e, 0x5f, 0xd6, 0xf9, 0x54, 0xae, 0x63, 0xd8, 0xf7, 0xcd, 0x54, 0x53, 0x39, 0x5b, 0x38,
	0x9a, 0x86, 0x11, 0xbf, 0x08, 0x02, 0xcc, 0xf7, 0x0e, 0xba, 0x77, 0x61, 0xc4, 0xaf, 0xa9, 0xa2,
	0x89, 0x6d, 0x0d, 0x9b, 0xa3, 0xc3, 0xf3, 0x13, 0x73, 0xa8, 0xc4, 0xf5, 0x78, 0xb0, 0xe0, 0xf1,
	0xb4, 0x80, 0xfd, 0x1d, 0x91, 0xbc, 0x31, 0xaa, 0x99, 0xb8, 0x93, 0x89, 0xdd, 0xd0, 0x2a, 0x52,
	0x57, 0xf9, 0x3b, 0x92, 0xf3, 0x09, 0x8e, 0x67, 0xe2, 0xb7, 0x0c, 0x59, 0x75, 0xf8, 0x2b, 0xe8,
	0x84, 0xa6, 0x59, 0x8c, 0x7e, 0x88, 0x26, 0x05, 0xd7, 0x47, 0xdc, 0xb9, 0x81, 0xfe, 0xd5, 0x92,
	0xb3, 0x9f, 0xd3, 0x48, 0x6e, 0x50, 0xfe, 0x11, 0x80, 0x61, 0x0f, 0xc3, 0x3f, 0x47, 0x07, 0xbc,
	0x8e, 0x52, 0xa5, 0xcf, 0x50, 0x11, 0x38, 0xef, 0xa1, 0xa7, 0xc1, 0x5d, 0x9a, 0xb6, 0x46, 0xd1,
	0xea, 0xb8, 0xb4, 0xca, 0xbb, 0x5a, 0x5e, 0x10, 0x1c, 0x06, 0x03, 0xf4, 0x9f, 0xc7, 0x54, 0x24,
	0xe6, 0x52, 0xd1, 0x68, 0x02, 0x3d, 0xb5, 0xeb, 0xa2, 0xdd, 0x8b, 0xfd, 0x64, 0x15, 0xe5, 0x35,
	0x57, 0x34, 0x8c, 0xfc, 0xff, 0x64, 0xce, 0x5f, 0x0b, 0x1e, 0x23, 0xf7, 0x26, 0xe5, 0x71, 0x86,
	0xfe, 0x7b, 0xbb, 0x63, 0xd5, 0x77, 0xe7, 0x03, 0xb4, 0x54, 0xb6, 0xe6, 0x7a, 0xad, 0x8e, 0xce,
	0x5f, 0xee, 0x4f, 0xae, 0xba, 0xb9, 0xba, 0x98, 0x67, 0x6b, 0xee, 0x6b, 0x0d, 0xb1, 0xa1, 0x93,
	0x5f, 0x5b, 0xee, 0x6c, 0x96, 0x0e, 0x4b, 0xe7, 0x35, 0x74, 0x4b, 0x32, 0xe9, 0x40, 0xf3, 0xc2,
	0xf3, 0xfa, 0xf7, 0xc8, 0x01, 0xb4, 0xa6, 0x33, 0x6f, 0xd2, 0xb7, 0xc8, 0x03, 0xe8, 0xe6, 0x7f,
	0xde, 0xcc, 0xfb, 0x36, 0xef, 0x37, 0x2e, 0x2f, 0xf3, 0xb7, 0xba, 0x72, 0xb7, 0x21, 0x15, 0x0b,
	0x41, 0xdd, 0xcd, 0x66, 0x83, 0x39, 0x30, 0xea, 0xf7, 0xb3, 0x45, 0xa8, 0x96, 0xe9, 0xad, 0xcb,
	0xe4, 0x6a, 0xbc, 0x15, 0x8c, 0x8d, 0x0d, 0xa1, 0x7c, 0x63, 0xb7, 0xe6, 0x31, 0xbf, 0xfd, 0x17,
	0x00, 0x00, 0xff, 0xff, 0x7d, 0x96, 0x87, 0x22, 0xf6, 0x03, 0x00, 0x00,
}
