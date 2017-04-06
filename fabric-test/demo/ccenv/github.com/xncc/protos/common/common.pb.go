// Code generated by protoc-gen-go.
// source: common/common.proto
// DO NOT EDIT!

/*
Package common is a generated protocol buffer package.

It is generated from these files:
	common/common.proto

It has these top-level messages:
	ChainCodeResponse
	AppVo
	OperateInfo
	LedgerFile
	LedgerFileMap
	OrgBankAccount
*/
package common

import proto "github.com/golang/protobuf/proto"
import fmt "fmt"
import math "math"
import google_protobuf "github.com/golang/protobuf/ptypes/timestamp"

// Reference imports to suppress errors if they are not otherwise used.
var _ = proto.Marshal
var _ = fmt.Errorf
var _ = math.Inf

// This is a compile-time assertion to ensure that this generated file
// is compatible with the proto package it is being compiled against.
// A compilation error at this line likely means your copy of the
// proto package needs to be updated.
const _ = proto.ProtoPackageIsVersion2 // please upgrade the proto package

// 调用 cc 返回的数据格式定义(通用)
type ChainCodeResponse struct {
	// 状态编码定义:
	// 0 = 成功
	// !0 = 失败错误码定义
	//      1000 = 参数格式错误
	//      1001 = 未知方法错误
	//      1002 = protobuf数据转换错误
	//      4004 = 数据不存在错误
	//      5000 = cc服务执行错误
	//      可以根据具体业务新增错误编码,编码格式统一定义在6000以后
	Code    int32  `protobuf:"varint,1,opt,name=code" json:"code,omitempty"`
	Message string `protobuf:"bytes,2,opt,name=message" json:"message,omitempty"`
	Result  []byte `protobuf:"bytes,3,opt,name=result,proto3" json:"result,omitempty"`
}

func (m *ChainCodeResponse) Reset()                    { *m = ChainCodeResponse{} }
func (m *ChainCodeResponse) String() string            { return proto.CompactTextString(m) }
func (*ChainCodeResponse) ProtoMessage()               {}
func (*ChainCodeResponse) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{0} }

func (m *ChainCodeResponse) GetCode() int32 {
	if m != nil {
		return m.Code
	}
	return 0
}

func (m *ChainCodeResponse) GetMessage() string {
	if m != nil {
		return m.Message
	}
	return ""
}

func (m *ChainCodeResponse) GetResult() []byte {
	if m != nil {
		return m.Result
	}
	return nil
}

// 机构摘要
type AppVo struct {
	AppId   string `protobuf:"bytes,1,opt,name=appId" json:"appId,omitempty"`
	AppName string `protobuf:"bytes,2,opt,name=appName" json:"appName,omitempty"`
	Type    string `protobuf:"bytes,3,opt,name=type" json:"type,omitempty"`
}

func (m *AppVo) Reset()                    { *m = AppVo{} }
func (m *AppVo) String() string            { return proto.CompactTextString(m) }
func (*AppVo) ProtoMessage()               {}
func (*AppVo) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{1} }

func (m *AppVo) GetAppId() string {
	if m != nil {
		return m.AppId
	}
	return ""
}

func (m *AppVo) GetAppName() string {
	if m != nil {
		return m.AppName
	}
	return ""
}

func (m *AppVo) GetType() string {
	if m != nil {
		return m.Type
	}
	return ""
}

// 数据操作信息
type OperateInfo struct {
	// 创建操作信息
	Creater    *AppVo                     `protobuf:"bytes,1,opt,name=creater" json:"creater,omitempty"`
	CreateTime *google_protobuf.Timestamp `protobuf:"bytes,2,opt,name=create_time,json=createTime" json:"create_time,omitempty"`
	CreaterIp  string                     `protobuf:"bytes,3,opt,name=creater_ip,json=createrIp" json:"creater_ip,omitempty"`
	// 更新操作信息
	LastUser *AppVo                     `protobuf:"bytes,4,opt,name=last_user,json=lastUser" json:"last_user,omitempty"`
	LastTime *google_protobuf.Timestamp `protobuf:"bytes,5,opt,name=last_time,json=lastTime" json:"last_time,omitempty"`
	LastIp   string                     `protobuf:"bytes,6,opt,name=last_ip,json=lastIp" json:"last_ip,omitempty"`
}

func (m *OperateInfo) Reset()                    { *m = OperateInfo{} }
func (m *OperateInfo) String() string            { return proto.CompactTextString(m) }
func (*OperateInfo) ProtoMessage()               {}
func (*OperateInfo) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{2} }

func (m *OperateInfo) GetCreater() *AppVo {
	if m != nil {
		return m.Creater
	}
	return nil
}

func (m *OperateInfo) GetCreateTime() *google_protobuf.Timestamp {
	if m != nil {
		return m.CreateTime
	}
	return nil
}

func (m *OperateInfo) GetCreaterIp() string {
	if m != nil {
		return m.CreaterIp
	}
	return ""
}

func (m *OperateInfo) GetLastUser() *AppVo {
	if m != nil {
		return m.LastUser
	}
	return nil
}

func (m *OperateInfo) GetLastTime() *google_protobuf.Timestamp {
	if m != nil {
		return m.LastTime
	}
	return nil
}

func (m *OperateInfo) GetLastIp() string {
	if m != nil {
		return m.LastIp
	}
	return ""
}

// 账本链上文件
type LedgerFile struct {
	Url         string       `protobuf:"bytes,1,opt,name=url" json:"url,omitempty"`
	Data        []byte       `protobuf:"bytes,2,opt,name=data,proto3" json:"data,omitempty"`
	Sha256      string       `protobuf:"bytes,3,opt,name=sha256" json:"sha256,omitempty"`
	Name        string       `protobuf:"bytes,4,opt,name=name" json:"name,omitempty"`
	Size        int64        `protobuf:"varint,5,opt,name=size" json:"size,omitempty"`
	Type        string       `protobuf:"bytes,6,opt,name=type" json:"type,omitempty"`
	IsEncrypted bool         `protobuf:"varint,7,opt,name=isEncrypted" json:"isEncrypted,omitempty"`
	OperateInfo *OperateInfo `protobuf:"bytes,8,opt,name=operate_info,json=operateInfo" json:"operate_info,omitempty"`
}

func (m *LedgerFile) Reset()                    { *m = LedgerFile{} }
func (m *LedgerFile) String() string            { return proto.CompactTextString(m) }
func (*LedgerFile) ProtoMessage()               {}
func (*LedgerFile) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{3} }

func (m *LedgerFile) GetUrl() string {
	if m != nil {
		return m.Url
	}
	return ""
}

func (m *LedgerFile) GetData() []byte {
	if m != nil {
		return m.Data
	}
	return nil
}

func (m *LedgerFile) GetSha256() string {
	if m != nil {
		return m.Sha256
	}
	return ""
}

func (m *LedgerFile) GetName() string {
	if m != nil {
		return m.Name
	}
	return ""
}

func (m *LedgerFile) GetSize() int64 {
	if m != nil {
		return m.Size
	}
	return 0
}

func (m *LedgerFile) GetType() string {
	if m != nil {
		return m.Type
	}
	return ""
}

func (m *LedgerFile) GetIsEncrypted() bool {
	if m != nil {
		return m.IsEncrypted
	}
	return false
}

func (m *LedgerFile) GetOperateInfo() *OperateInfo {
	if m != nil {
		return m.OperateInfo
	}
	return nil
}

// 账本链上文件集合
type LedgerFileMap struct {
	Files map[string]*LedgerFile `protobuf:"bytes,1,rep,name=files" json:"files,omitempty" protobuf_key:"bytes,1,opt,name=key" protobuf_val:"bytes,2,opt,name=value"`
}

func (m *LedgerFileMap) Reset()                    { *m = LedgerFileMap{} }
func (m *LedgerFileMap) String() string            { return proto.CompactTextString(m) }
func (*LedgerFileMap) ProtoMessage()               {}
func (*LedgerFileMap) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{4} }

func (m *LedgerFileMap) GetFiles() map[string]*LedgerFile {
	if m != nil {
		return m.Files
	}
	return nil
}

// 银行账户信息
type OrgBankAccount struct {
	Account    string `protobuf:"bytes,1,opt,name=account" json:"account,omitempty"`
	BankName   string `protobuf:"bytes,2,opt,name=bank_name,json=bankName" json:"bank_name,omitempty"`
	BankBranch string `protobuf:"bytes,3,opt,name=bank_branch,json=bankBranch" json:"bank_branch,omitempty"`
}

func (m *OrgBankAccount) Reset()                    { *m = OrgBankAccount{} }
func (m *OrgBankAccount) String() string            { return proto.CompactTextString(m) }
func (*OrgBankAccount) ProtoMessage()               {}
func (*OrgBankAccount) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{5} }

func (m *OrgBankAccount) GetAccount() string {
	if m != nil {
		return m.Account
	}
	return ""
}

func (m *OrgBankAccount) GetBankName() string {
	if m != nil {
		return m.BankName
	}
	return ""
}

func (m *OrgBankAccount) GetBankBranch() string {
	if m != nil {
		return m.BankBranch
	}
	return ""
}

func init() {
	proto.RegisterType((*ChainCodeResponse)(nil), "protos.ChainCodeResponse")
	proto.RegisterType((*AppVo)(nil), "protos.AppVo")
	proto.RegisterType((*OperateInfo)(nil), "protos.OperateInfo")
	proto.RegisterType((*LedgerFile)(nil), "protos.LedgerFile")
	proto.RegisterType((*LedgerFileMap)(nil), "protos.LedgerFileMap")
	proto.RegisterType((*OrgBankAccount)(nil), "protos.OrgBankAccount")
}

func init() { proto.RegisterFile("common/common.proto", fileDescriptor0) }

var fileDescriptor0 = []byte{
	// 592 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x09, 0x6e, 0x88, 0x02, 0xff, 0x84, 0x54, 0x5f, 0x6b, 0xd4, 0x4e,
	0x14, 0x25, 0xdd, 0xee, 0xbf, 0x9b, 0xf6, 0xc7, 0xcf, 0xa9, 0x68, 0xa8, 0x94, 0x86, 0xbc, 0x18,
	0x7c, 0xc8, 0xc2, 0x8a, 0x55, 0x14, 0x84, 0xb6, 0x54, 0x58, 0xac, 0x16, 0x06, 0x15, 0xf4, 0x65,
	0x99, 0x4d, 0x66, 0xb3, 0x61, 0x93, 0x99, 0x61, 0x66, 0x62, 0xbb, 0x7e, 0x0a, 0x5f, 0xfc, 0x7e,
	0x7e, 0x14, 0x99, 0x3f, 0xe9, 0x56, 0x14, 0x7c, 0x9a, 0x7b, 0xee, 0xdc, 0xdc, 0x39, 0xf7, 0x9c,
	0xc9, 0xc0, 0x41, 0xce, 0x9b, 0x86, 0xb3, 0x89, 0x5b, 0x32, 0x21, 0xb9, 0xe6, 0x68, 0x60, 0x17,
	0x75, 0x78, 0x5c, 0x72, 0x5e, 0xd6, 0x74, 0x62, 0xe1, 0xa2, 0x5d, 0x4e, 0x74, 0xd5, 0x50, 0xa5,
	0x49, 0x23, 0x5c, 0x61, 0xf2, 0x19, 0xee, 0x9d, 0xaf, 0x48, 0xc5, 0xce, 0x79, 0x41, 0x31, 0x55,
	0x82, 0x33, 0x45, 0x11, 0x82, 0xdd, 0x9c, 0x17, 0x34, 0x0a, 0xe2, 0x20, 0xed, 0x63, 0x1b, 0xa3,
	0x08, 0x86, 0x0d, 0x55, 0x8a, 0x94, 0x34, 0xda, 0x89, 0x83, 0x74, 0x8c, 0x3b, 0x88, 0x1e, 0xc0,
	0x40, 0x52, 0xd5, 0xd6, 0x3a, 0xea, 0xc5, 0x41, 0xba, 0x87, 0x3d, 0x4a, 0xde, 0x42, 0xff, 0x54,
	0x88, 0x4f, 0x1c, 0xdd, 0x87, 0x3e, 0x11, 0x62, 0x56, 0xd8, 0x7e, 0x63, 0xec, 0x80, 0x69, 0x48,
	0x84, 0x78, 0x4f, 0x9a, 0xdb, 0x86, 0x1e, 0x9a, 0xe3, 0xf5, 0x46, 0x50, 0xdb, 0x6e, 0x8c, 0x6d,
	0x9c, 0x7c, 0xdf, 0x81, 0xf0, 0x4a, 0x50, 0x49, 0x34, 0x9d, 0xb1, 0x25, 0x47, 0x8f, 0x61, 0x98,
	0x4b, 0x4a, 0x34, 0x95, 0xb6, 0x6b, 0x38, 0xdd, 0x77, 0x03, 0xa9, 0xcc, 0x9e, 0x89, 0xbb, 0x5d,
	0xf4, 0x0a, 0x42, 0x17, 0xce, 0xcd, 0xe8, 0xf6, 0xa8, 0x70, 0x7a, 0x98, 0x39, 0x5d, 0xb2, 0x4e,
	0x97, 0xec, 0x43, 0xa7, 0x0b, 0x06, 0x57, 0x6e, 0x12, 0xe8, 0x08, 0x3c, 0x92, 0xf3, 0x4a, 0x78,
	0x3e, 0x63, 0x9f, 0x99, 0x09, 0xf4, 0x04, 0xc6, 0x35, 0x51, 0x7a, 0xde, 0x2a, 0x2a, 0xa3, 0xdd,
	0xbf, 0xd1, 0x18, 0x99, 0xfd, 0x8f, 0x8a, 0x4a, 0xf4, 0xdc, 0xd7, 0x5a, 0x16, 0xfd, 0x7f, 0xb2,
	0xb0, 0x1f, 0x5a, 0x0e, 0x0f, 0x61, 0x68, 0x3f, 0xac, 0x44, 0x34, 0xb0, 0x04, 0x06, 0x06, 0xce,
	0x44, 0xf2, 0x33, 0x00, 0xb8, 0xa4, 0x45, 0x49, 0xe5, 0x9b, 0xaa, 0xa6, 0xe8, 0x7f, 0xe8, 0xb5,
	0xb2, 0xf6, 0x1a, 0x9b, 0xd0, 0xe8, 0x58, 0x10, 0x4d, 0xec, 0xcc, 0x7b, 0xd8, 0xc6, 0xc6, 0x2c,
	0xb5, 0x22, 0xd3, 0x67, 0x27, 0x7e, 0x1a, 0x8f, 0x4c, 0x2d, 0x33, 0x56, 0xec, 0x3a, 0xcd, 0x99,
	0xf7, 0x41, 0x55, 0xdf, 0x1c, 0xdb, 0x1e, 0xb6, 0xf1, 0xad, 0x37, 0x83, 0xad, 0x37, 0x28, 0x86,
	0xb0, 0x52, 0x17, 0x2c, 0x97, 0x1b, 0xa1, 0x69, 0x11, 0x0d, 0xe3, 0x20, 0x1d, 0xe1, 0xbb, 0x29,
	0x74, 0x02, 0x7b, 0xdc, 0x99, 0x37, 0xaf, 0xd8, 0x92, 0x47, 0x23, 0x3b, 0xff, 0x41, 0xa7, 0xd5,
	0x1d, 0x63, 0x71, 0xc8, 0xb7, 0x20, 0xf9, 0x11, 0xc0, 0xfe, 0x76, 0xc4, 0x77, 0x44, 0xa0, 0x13,
	0xe8, 0x2f, 0xab, 0x9a, 0xaa, 0x28, 0x88, 0x7b, 0x69, 0x38, 0x8d, 0xbb, 0x16, 0xbf, 0x55, 0x65,
	0x66, 0x55, 0x17, 0x4c, 0xcb, 0x0d, 0x76, 0xe5, 0x87, 0x97, 0x00, 0xdb, 0xa4, 0xd1, 0x6a, 0x4d,
	0x37, 0x9d, 0x56, 0x6b, 0xba, 0x41, 0x29, 0xf4, 0xbf, 0x92, 0xba, 0xed, 0x2e, 0x08, 0xfa, 0xb3,
	0x2f, 0x76, 0x05, 0x2f, 0x77, 0x5e, 0x04, 0xc9, 0x0a, 0xfe, 0xbb, 0x92, 0xe5, 0x19, 0x61, 0xeb,
	0xd3, 0x3c, 0xe7, 0x2d, 0xd3, 0xf6, 0x36, 0xbb, 0xd0, 0x77, 0xed, 0x20, 0x7a, 0x04, 0xe3, 0x05,
	0x61, 0xeb, 0x39, 0xdb, 0xde, 0xf4, 0x91, 0x49, 0xd8, 0xab, 0x7e, 0x0c, 0xa1, 0xdd, 0x5c, 0x48,
	0xc2, 0xf2, 0x95, 0xf7, 0x04, 0x4c, 0xea, 0xcc, 0x66, 0xce, 0x5e, 0xc3, 0x51, 0xce, 0x9b, 0xec,
	0xa6, 0x22, 0xac, 0x64, 0x24, 0xbb, 0xbe, 0xbe, 0xee, 0x98, 0xb9, 0xff, 0xfd, 0xcb, 0x51, 0x59,
	0xe9, 0x55, 0xbb, 0x30, 0x70, 0x72, 0xc3, 0xf2, 0xdc, 0xfd, 0xea, 0xca, 0x3f, 0x07, 0x0b, 0xf7,
	0x10, 0x3c, 0xfd, 0x15, 0x00, 0x00, 0xff, 0xff, 0x6a, 0x64, 0xb8, 0x29, 0x26, 0x04, 0x00, 0x00,
}
