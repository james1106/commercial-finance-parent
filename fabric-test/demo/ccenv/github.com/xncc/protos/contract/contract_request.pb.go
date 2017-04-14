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
	return fileDescriptor3, []int{5, 0}
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

// 合约查询请求参数
type ContractQueryRequest struct {
	ContractKey string                         `protobuf:"bytes,1,opt,name=contractKey" json:"contractKey,omitempty"`
	Type        ContractQueryRequest_QueryType `protobuf:"varint,2,opt,name=type,enum=protos.ContractQueryRequest_QueryType" json:"type,omitempty"`
	FileKey     string                         `protobuf:"bytes,3,opt,name=fileKey" json:"fileKey,omitempty"`
}

func (m *ContractQueryRequest) Reset()                    { *m = ContractQueryRequest{} }
func (m *ContractQueryRequest) String() string            { return proto.CompactTextString(m) }
func (*ContractQueryRequest) ProtoMessage()               {}
func (*ContractQueryRequest) Descriptor() ([]byte, []int) { return fileDescriptor3, []int{5} }

func init() {
	proto.RegisterType((*ContractExcuteRequest)(nil), "protos.ContractExcuteRequest")
	proto.RegisterType((*FileAddRequest)(nil), "protos.FileAddRequest")
	proto.RegisterType((*InvoiceAddRequest)(nil), "protos.InvoiceAddRequest")
	proto.RegisterType((*CheckFlowRequest)(nil), "protos.CheckFlowRequest")
	proto.RegisterType((*CheckRequest)(nil), "protos.CheckRequest")
	proto.RegisterType((*ContractQueryRequest)(nil), "protos.ContractQueryRequest")
	proto.RegisterEnum("protos.ContractQueryRequest_QueryType", ContractQueryRequest_QueryType_name, ContractQueryRequest_QueryType_value)
}

func init() { proto.RegisterFile("contract/contract_request.proto", fileDescriptor3) }

var fileDescriptor3 = []byte{
	// 456 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x09, 0x6e, 0x88, 0x02, 0xff, 0x64, 0x53, 0x4f, 0x6f, 0xd3, 0x30,
	0x14, 0x27, 0x6d, 0x69, 0xd7, 0xb7, 0x31, 0x3a, 0x03, 0xc3, 0xaa, 0xc4, 0x56, 0xe5, 0x80, 0xca,
	0x81, 0x14, 0x0d, 0x2e, 0x20, 0x81, 0xb4, 0x8d, 0x55, 0xaa, 0x88, 0x40, 0x0b, 0x3b, 0x71, 0x41,
	0x9e, 0xeb, 0x75, 0x11, 0xa9, 0x5d, 0x12, 0x87, 0x24, 0x1f, 0x88, 0x8f, 0xc1, 0x77, 0x43, 0xb1,
	0xf3, 0xd2, 0xa8, 0x39, 0xc5, 0xef, 0xfd, 0xfe, 0xbc, 0x9f, 0xfb, 0x6a, 0x38, 0xe5, 0x4a, 0xea,
	0x98, 0x71, 0x3d, 0xc3, 0xc3, 0xcf, 0x58, 0xfc, 0x4e, 0x45, 0xa2, 0xbd, 0x4d, 0xac, 0xb4, 0x22,
	0x7d, 0xf3, 0x49, 0xc6, 0x4f, 0xb8, 0x5a, 0xaf, 0x95, 0x9c, 0xd9, 0x8f, 0x05, 0xc7, 0xcf, 0x5b,
	0xea, 0x0a, 0x38, 0x69, 0xdb, 0x26, 0x9a, 0xe9, 0x34, 0xb1, 0xb8, 0xfb, 0xd7, 0x81, 0x67, 0x97,
	0x15, 0x72, 0x95, 0xf3, 0x54, 0x8b, 0xc0, 0x4e, 0x25, 0xc7, 0xd0, 0x67, 0x5c, 0x87, 0x4a, 0x52,
	0x67, 0xe2, 0x4c, 0x87, 0x41, 0x55, 0x91, 0x09, 0xec, 0xa3, 0xd5, 0x17, 0x51, 0xd0, 0x8e, 0x01,
	0x9b, 0x2d, 0x72, 0x02, 0x80, 0xe5, 0xd7, 0x6f, 0xb4, 0x6b, 0x08, 0x8d, 0x0e, 0x19, 0xc3, 0xde,
	0x26, 0x62, 0x45, 0xa4, 0xd8, 0x92, 0xf6, 0x26, 0xdd, 0xe9, 0x41, 0x50, 0xd7, 0xe5, 0x54, 0x61,
	0x62, 0xd0, 0x87, 0x76, 0xaa, 0xad, 0xdc, 0x1c, 0x0e, 0xe7, 0x61, 0x24, 0xce, 0x97, 0x4b, 0xcc,
	0xf7, 0x0e, 0x86, 0x77, 0x61, 0x24, 0x3e, 0x33, 0xcd, 0x12, 0xea, 0x4c, 0xba, 0xd3, 0xfd, 0xb3,
	0x63, 0x7b, 0xa9, 0xc4, 0xf3, 0xc5, 0x72, 0x25, 0xe2, 0x79, 0x05, 0x07, 0x5b, 0x22, 0x79, 0x63,
	0x55, 0x0b, 0x79, 0xa7, 0x12, 0xda, 0x31, 0x2a, 0xd2, 0x56, 0x05, 0x5b, 0x92, 0xfb, 0x09, 0x8e,
	0x16, 0xf2, 0x8f, 0x0a, 0x79, 0x73, 0xf8, 0x2b, 0x18, 0x84, 0xb6, 0x59, 0x8d, 0x7e, 0x8c, 0x26,
	0x15, 0x37, 0x40, 0xdc, 0xbd, 0x86, 0xd1, 0xe5, 0xbd, 0xe0, 0xbf, 0xe6, 0x91, 0xca, 0x50, 0xfe,
	0x11, 0x80, 0x63, 0x0f, 0xc3, 0xbf, 0x40, 0x07, 0x5c, 0x47, 0xad, 0x32, 0x77, 0x68, 0x08, 0xdc,
	0xf7, 0x70, 0x60, 0xc0, 0x6d, 0x9a, 0xbe, 0x41, 0xd1, 0xea, 0xa8, 0xb6, 0x2a, 0xbb, 0x46, 0x5e,
	0x11, 0xdc, 0x7f, 0x0e, 0x3c, 0xc5, 0x01, 0xd7, 0xa9, 0x88, 0x0b, 0xf4, 0xd8, 0x59, 0xab, 0xd3,
	0x5e, 0xeb, 0x07, 0xe8, 0xe9, 0x62, 0x23, 0xcc, 0xc6, 0x0f, 0xcf, 0x5e, 0xee, 0xc6, 0x6d, 0xba,
	0x79, 0xa6, 0xb8, 0x29, 0x36, 0x22, 0x30, 0x1a, 0x42, 0x61, 0x50, 0xfe, 0xa2, 0xa5, 0xb3, 0xfd,
	0x3f, 0x60, 0xe9, 0xbe, 0x86, 0x61, 0x4d, 0x26, 0x03, 0xe8, 0x9e, 0xfb, 0xfe, 0xe8, 0x01, 0xd9,
	0x83, 0xde, 0x7c, 0xe1, 0x5f, 0x8d, 0x1c, 0xf2, 0x08, 0x86, 0xe5, 0xc9, 0x5f, 0xf8, 0xdf, 0x6f,
	0x46, 0x9d, 0x8b, 0x8b, 0xf2, 0xa1, 0xac, 0xbd, 0x3c, 0x64, 0x72, 0x25, 0x99, 0x97, 0x65, 0x19,
	0xe6, 0xc0, 0xa8, 0x3f, 0x4e, 0x57, 0xa1, 0xbe, 0x4f, 0x6f, 0x3d, 0xae, 0xd6, 0xb3, 0x5c, 0x72,
	0x3e, 0xb3, 0x84, 0xfa, 0x01, 0xdc, 0xda, 0x97, 0xf4, 0xf6, 0x7f, 0x00, 0x00, 0x00, 0xff, 0xff,
	0x92, 0x83, 0x2a, 0xfb, 0x73, 0x03, 0x00, 0x00,
}
