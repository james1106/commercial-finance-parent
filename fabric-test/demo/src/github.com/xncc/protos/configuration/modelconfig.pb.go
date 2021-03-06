// Code generated by protoc-gen-go.
// source: configuration/modelconfig.proto
// DO NOT EDIT!

package configuration

import proto "github.com/golang/protobuf/proto"
import fmt "fmt"
import math "math"
import protos "github.com/xncc/protos/common"

// Reference imports to suppress errors if they are not otherwise used.
var _ = proto.Marshal
var _ = fmt.Errorf
var _ = math.Inf

// 三方机构关系上链数据定义
type MultiPartiesRelation struct {
	Supplier   *CommercialOrg `protobuf:"bytes,2,opt,name=supplier" json:"supplier,omitempty"`
	Enterprise *CommercialOrg `protobuf:"bytes,3,opt,name=enterprise" json:"enterprise,omitempty"`
	Factoring  *CommercialOrg `protobuf:"bytes,4,opt,name=factoring" json:"factoring,omitempty"`
	// 三方机构关系确立时可以设定默认的融资利率
	Rate int64 `protobuf:"varint,5,opt,name=rate" json:"rate,omitempty"`
	// 三方机构关系确立的相关合同文件
	Files []*protos.LedgerFile `protobuf:"bytes,6,rep,name=files" json:"files,omitempty"`
	// 保理向银行融资贷款时,勾选此三方的融资订单为质押
	// 银行需要拥有此三方账本,此字段只是备注记录,不做他用
	BankUserinfos []*UserInfo `protobuf:"bytes,7,rep,name=bank_userinfos,json=bankUserinfos" json:"bank_userinfos,omitempty"`
}

func (m *MultiPartiesRelation) Reset()                    { *m = MultiPartiesRelation{} }
func (m *MultiPartiesRelation) String() string            { return proto.CompactTextString(m) }
func (*MultiPartiesRelation) ProtoMessage()               {}
func (*MultiPartiesRelation) Descriptor() ([]byte, []int) { return fileDescriptor2, []int{0} }

func (m *MultiPartiesRelation) GetSupplier() *CommercialOrg {
	if m != nil {
		return m.Supplier
	}
	return nil
}

func (m *MultiPartiesRelation) GetEnterprise() *CommercialOrg {
	if m != nil {
		return m.Enterprise
	}
	return nil
}

func (m *MultiPartiesRelation) GetFactoring() *CommercialOrg {
	if m != nil {
		return m.Factoring
	}
	return nil
}

func (m *MultiPartiesRelation) GetFiles() []*protos.LedgerFile {
	if m != nil {
		return m.Files
	}
	return nil
}

func (m *MultiPartiesRelation) GetBankUserinfos() []*UserInfo {
	if m != nil {
		return m.BankUserinfos
	}
	return nil
}

// 银行保理双方关系上链数据定义
type TwoPartiesRelation struct {
	Factoring *CommercialOrg `protobuf:"bytes,2,opt,name=factoring" json:"factoring,omitempty"`
	Bank      *CommercialOrg `protobuf:"bytes,3,opt,name=bank" json:"bank,omitempty"`
	// 双方机构关系确立时可以设定默认的融资利率
	Rate int64 `protobuf:"varint,4,opt,name=rate" json:"rate,omitempty"`
	// 双方机构关系确立的相关合同文件
	Files []*protos.LedgerFile `protobuf:"bytes,5,rep,name=files" json:"files,omitempty"`
}

func (m *TwoPartiesRelation) Reset()                    { *m = TwoPartiesRelation{} }
func (m *TwoPartiesRelation) String() string            { return proto.CompactTextString(m) }
func (*TwoPartiesRelation) ProtoMessage()               {}
func (*TwoPartiesRelation) Descriptor() ([]byte, []int) { return fileDescriptor2, []int{1} }

func (m *TwoPartiesRelation) GetFactoring() *CommercialOrg {
	if m != nil {
		return m.Factoring
	}
	return nil
}

func (m *TwoPartiesRelation) GetBank() *CommercialOrg {
	if m != nil {
		return m.Bank
	}
	return nil
}

func (m *TwoPartiesRelation) GetFiles() []*protos.LedgerFile {
	if m != nil {
		return m.Files
	}
	return nil
}

func init() {
	proto.RegisterType((*MultiPartiesRelation)(nil), "protos.MultiPartiesRelation")
	proto.RegisterType((*TwoPartiesRelation)(nil), "protos.TwoPartiesRelation")
}

func init() { proto.RegisterFile("configuration/modelconfig.proto", fileDescriptor2) }

var fileDescriptor2 = []byte{
	// 334 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x09, 0x6e, 0x88, 0x02, 0xff, 0x8c, 0x92, 0x41, 0x4b, 0xf3, 0x40,
	0x10, 0x86, 0x69, 0x9b, 0xf6, 0xfb, 0xdc, 0xa2, 0xc8, 0xaa, 0x10, 0x0a, 0x62, 0x29, 0x3d, 0xc4,
	0x4b, 0x82, 0x2d, 0xe2, 0x5d, 0x41, 0x10, 0x2a, 0x4a, 0xb0, 0x17, 0x2f, 0xb2, 0xdd, 0x4e, 0xe2,
	0x62, 0x76, 0x27, 0xec, 0x6e, 0x48, 0x7f, 0x94, 0x27, 0x7f, 0xa1, 0x34, 0x4b, 0x6a, 0x23, 0x52,
	0x3d, 0xcd, 0xb2, 0xf3, 0x3c, 0x03, 0xef, 0x30, 0xe4, 0x8c, 0xa3, 0x4a, 0x44, 0x5a, 0x68, 0x66,
	0x05, 0xaa, 0x48, 0xe2, 0x12, 0x32, 0xf7, 0x15, 0xe6, 0x1a, 0x2d, 0xd2, 0x5e, 0x55, 0xcc, 0xe0,
	0x88, 0xa3, 0x94, 0xa8, 0x22, 0x57, 0x5c, 0x73, 0x70, 0xda, 0xb4, 0x59, 0x9e, 0x6f, 0xbb, 0xa3,
	0xf7, 0x36, 0x39, 0xbe, 0x2f, 0x32, 0x2b, 0x1e, 0x99, 0xb6, 0x02, 0x4c, 0x0c, 0x59, 0x05, 0xd2,
	0x0b, 0xf2, 0xdf, 0x14, 0x79, 0x9e, 0x09, 0xd0, 0x7e, 0x7b, 0xd8, 0x0a, 0xfa, 0x93, 0x13, 0xa7,
	0x98, 0xf0, 0x06, 0xa5, 0x04, 0xcd, 0x05, 0xcb, 0x1e, 0x74, 0x1a, 0x6f, 0x30, 0x7a, 0x49, 0x08,
	0x28, 0x0b, 0x3a, 0xd7, 0xc2, 0x80, 0xdf, 0xd9, 0x25, 0x6d, 0x81, 0x74, 0x4a, 0xf6, 0x12, 0xc6,
	0x2d, 0x6a, 0xa1, 0x52, 0xdf, 0xdb, 0x65, 0x7d, 0x71, 0x94, 0x12, 0x4f, 0x33, 0x0b, 0x7e, 0x77,
	0xd8, 0x0a, 0x3a, 0x71, 0xf5, 0xa6, 0x01, 0xe9, 0x26, 0x22, 0x03, 0xe3, 0xf7, 0x86, 0x9d, 0xa0,
	0x3f, 0xa1, 0xf5, 0x90, 0x19, 0x2c, 0x53, 0xd0, 0xb7, 0x22, 0x83, 0xd8, 0x01, 0xf4, 0x8a, 0x1c,
	0x2c, 0x98, 0x7a, 0x7b, 0x29, 0x0c, 0x68, 0xa1, 0x12, 0x34, 0xfe, 0xbf, 0x4a, 0x39, 0xac, 0x95,
	0xb9, 0x01, 0x7d, 0xa7, 0x12, 0x8c, 0xf7, 0xd7, 0xdc, 0xbc, 0xc6, 0x46, 0x1f, 0x2d, 0x42, 0x9f,
	0x4a, 0xfc, 0xbe, 0xac, 0x46, 0x84, 0xf6, 0x1f, 0x23, 0x9c, 0x13, 0x6f, 0x3d, 0x7c, 0xf7, 0xa2,
	0x2a, 0x64, 0x93, 0xd6, 0xfb, 0x29, 0x6d, 0xf7, 0x97, 0xb4, 0xd7, 0x33, 0x32, 0xe6, 0x28, 0xc3,
	0x95, 0x60, 0x2a, 0x55, 0x2c, 0x2c, 0xcb, 0xb2, 0x66, 0x1b, 0xb7, 0xf1, 0x3c, 0x4e, 0x85, 0x7d,
	0x2d, 0x16, 0x21, 0x47, 0x19, 0xad, 0x14, 0xe7, 0x91, 0xa3, 0xa2, 0x06, 0xb5, 0x70, 0xd7, 0x36,
	0xfd, 0x0c, 0x00, 0x00, 0xff, 0xff, 0xcd, 0x8b, 0x19, 0x55, 0x97, 0x02, 0x00, 0x00,
}
