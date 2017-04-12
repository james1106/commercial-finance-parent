// Code generated by protoc-gen-go.
// source: configuration/appconfig.proto
// DO NOT EDIT!

/*
Package configuration is a generated protocol buffer package.

It is generated from these files:
	configuration/appconfig.proto
	configuration/contractflow.proto
	configuration/modelconfig.proto

It has these top-level messages:
	UserPrivateInfo
	ContactPerson
	MultiPartiesInfo
	TwoPartiesInfo
	MultiPInfos
	TwoPInfos
	UserInfo
	CommercialOrg
	ContractStep
	ContractFlow
	MultiPartiesRelation
	TwoPartiesRelation
*/
package configuration

import proto "github.com/golang/protobuf/proto"
import fmt "fmt"
import math "math"
import protos "github.com/xncc/protos/common"

// Reference imports to suppress errors if they are not otherwise used.
var _ = proto.Marshal
var _ = fmt.Errorf
var _ = math.Inf

// This is a compile-time assertion to ensure that this generated file
// is compatible with the proto package it is being compiled against.
// A compilation error at this line likely means your copy of the
// proto package needs to be updated.
const _ = proto.ProtoPackageIsVersion2 // please upgrade the proto package

// ======================================================私有配置信息=======================================
// key 为 APPID
type UserPrivateInfo struct {
	UserId         string         `protobuf:"bytes,1,opt,name=user_id,json=userId" json:"user_id,omitempty"`
	PublicKey      string         `protobuf:"bytes,2,opt,name=public_key,json=publicKey" json:"public_key,omitempty"`
	PrivateKey     string         `protobuf:"bytes,3,opt,name=private_key,json=privateKey" json:"private_key,omitempty"`
	SdkUserName    string         `protobuf:"bytes,4,opt,name=sdk_user_name,json=sdkUserName" json:"sdk_user_name,omitempty"`
	SdkUserPwd     string         `protobuf:"bytes,5,opt,name=sdk_user_pwd,json=sdkUserPwd" json:"sdk_user_pwd,omitempty"`
	SdkEcert       string         `protobuf:"bytes,6,opt,name=sdk_ecert,json=sdkEcert" json:"sdk_ecert,omitempty"`
	BuzContact     *ContactPerson `protobuf:"bytes,7,opt,name=buz_contact,json=buzContact" json:"buz_contact,omitempty"`
	TechContact    *ContactPerson `protobuf:"bytes,8,opt,name=tech_contact,json=techContact" json:"tech_contact,omitempty"`
	AppCallbackUrl string         `protobuf:"bytes,9,opt,name=app_callback_url,json=appCallbackUrl" json:"app_callback_url,omitempty"`
	SecureIps      string         `protobuf:"bytes,10,opt,name=secure_ips,json=secureIps" json:"secure_ips,omitempty"`
}

func (m *UserPrivateInfo) Reset()                    { *m = UserPrivateInfo{} }
func (m *UserPrivateInfo) String() string            { return proto.CompactTextString(m) }
func (*UserPrivateInfo) ProtoMessage()               {}
func (*UserPrivateInfo) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{0} }

func (m *UserPrivateInfo) GetBuzContact() *ContactPerson {
	if m != nil {
		return m.BuzContact
	}
	return nil
}

func (m *UserPrivateInfo) GetTechContact() *ContactPerson {
	if m != nil {
		return m.TechContact
	}
	return nil
}

// ======================================================公有配置信息=======================================
type ContactPerson struct {
	Name   string `protobuf:"bytes,1,opt,name=name" json:"name,omitempty"`
	Mobile string `protobuf:"bytes,2,opt,name=mobile" json:"mobile,omitempty"`
	Email  string `protobuf:"bytes,3,opt,name=email" json:"email,omitempty"`
}

func (m *ContactPerson) Reset()                    { *m = ContactPerson{} }
func (m *ContactPerson) String() string            { return proto.CompactTextString(m) }
func (*ContactPerson) ProtoMessage()               {}
func (*ContactPerson) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{1} }

type MultiPartiesInfo struct {
	LedgerId   string              `protobuf:"bytes,1,opt,name=ledger_id,json=ledgerId" json:"ledger_id,omitempty"`
	Enterprise *UserInfo           `protobuf:"bytes,2,opt,name=enterprise" json:"enterprise,omitempty"`
	Factoring  *UserInfo           `protobuf:"bytes,3,opt,name=factoring" json:"factoring,omitempty"`
	Supplier   *UserInfo           `protobuf:"bytes,4,opt,name=supplier" json:"supplier,omitempty"`
	BankInfos  []*UserInfo         `protobuf:"bytes,5,rep,name=bank_infos,json=bankInfos" json:"bank_infos,omitempty"`
	OpInfo     *protos.OperateInfo `protobuf:"bytes,17,opt,name=op_info,json=opInfo" json:"op_info,omitempty"`
}

func (m *MultiPartiesInfo) Reset()                    { *m = MultiPartiesInfo{} }
func (m *MultiPartiesInfo) String() string            { return proto.CompactTextString(m) }
func (*MultiPartiesInfo) ProtoMessage()               {}
func (*MultiPartiesInfo) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{2} }

func (m *MultiPartiesInfo) GetEnterprise() *UserInfo {
	if m != nil {
		return m.Enterprise
	}
	return nil
}

func (m *MultiPartiesInfo) GetFactoring() *UserInfo {
	if m != nil {
		return m.Factoring
	}
	return nil
}

func (m *MultiPartiesInfo) GetSupplier() *UserInfo {
	if m != nil {
		return m.Supplier
	}
	return nil
}

func (m *MultiPartiesInfo) GetBankInfos() []*UserInfo {
	if m != nil {
		return m.BankInfos
	}
	return nil
}

func (m *MultiPartiesInfo) GetOpInfo() *protos.OperateInfo {
	if m != nil {
		return m.OpInfo
	}
	return nil
}

type TwoPartiesInfo struct {
	LedgerId  string              `protobuf:"bytes,1,opt,name=ledger_id,json=ledgerId" json:"ledger_id,omitempty"`
	BankInfo  *UserInfo           `protobuf:"bytes,2,opt,name=bank_info,json=bankInfo" json:"bank_info,omitempty"`
	Factoring *UserInfo           `protobuf:"bytes,3,opt,name=factoring" json:"factoring,omitempty"`
	OpInfo    *protos.OperateInfo `protobuf:"bytes,17,opt,name=op_info,json=opInfo" json:"op_info,omitempty"`
}

func (m *TwoPartiesInfo) Reset()                    { *m = TwoPartiesInfo{} }
func (m *TwoPartiesInfo) String() string            { return proto.CompactTextString(m) }
func (*TwoPartiesInfo) ProtoMessage()               {}
func (*TwoPartiesInfo) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{3} }

func (m *TwoPartiesInfo) GetBankInfo() *UserInfo {
	if m != nil {
		return m.BankInfo
	}
	return nil
}

func (m *TwoPartiesInfo) GetFactoring() *UserInfo {
	if m != nil {
		return m.Factoring
	}
	return nil
}

func (m *TwoPartiesInfo) GetOpInfo() *protos.OperateInfo {
	if m != nil {
		return m.OpInfo
	}
	return nil
}

// key 为 MP-userID
type MultiPInfos struct {
	Infos []*MultiPartiesInfo `protobuf:"bytes,1,rep,name=infos" json:"infos,omitempty"`
}

func (m *MultiPInfos) Reset()                    { *m = MultiPInfos{} }
func (m *MultiPInfos) String() string            { return proto.CompactTextString(m) }
func (*MultiPInfos) ProtoMessage()               {}
func (*MultiPInfos) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{4} }

func (m *MultiPInfos) GetInfos() []*MultiPartiesInfo {
	if m != nil {
		return m.Infos
	}
	return nil
}

// key 为 TP-userID
type TwoPInfos struct {
	Infos []*TwoPartiesInfo `protobuf:"bytes,1,rep,name=infos" json:"infos,omitempty"`
}

func (m *TwoPInfos) Reset()                    { *m = TwoPInfos{} }
func (m *TwoPInfos) String() string            { return proto.CompactTextString(m) }
func (*TwoPInfos) ProtoMessage()               {}
func (*TwoPInfos) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{5} }

func (m *TwoPInfos) GetInfos() []*TwoPartiesInfo {
	if m != nil {
		return m.Infos
	}
	return nil
}

// key 为 userID
type UserInfo struct {
	UserId    string `protobuf:"bytes,1,opt,name=user_id,json=userId" json:"user_id,omitempty"`
	AppName   string `protobuf:"bytes,2,opt,name=app_name,json=appName" json:"app_name,omitempty"`
	AppType   string `protobuf:"bytes,3,opt,name=app_type,json=appType" json:"app_type,omitempty"`
	PublicKey string `protobuf:"bytes,4,opt,name=public_key,json=publicKey" json:"public_key,omitempty"`
}

func (m *UserInfo) Reset()                    { *m = UserInfo{} }
func (m *UserInfo) String() string            { return proto.CompactTextString(m) }
func (*UserInfo) ProtoMessage()               {}
func (*UserInfo) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{6} }

// key 为 ORG-userID
type CommercialOrg struct {
	UserId             string                 `protobuf:"bytes,1,opt,name=user_id,json=userId" json:"user_id,omitempty"`
	OrgName            string                 `protobuf:"bytes,4,opt,name=org_name,json=orgName" json:"org_name,omitempty"`
	OrgAddress         string                 `protobuf:"bytes,5,opt,name=org_address,json=orgAddress" json:"org_address,omitempty"`
	OrgRegisterAddress string                 `protobuf:"bytes,6,opt,name=org_register_address,json=orgRegisterAddress" json:"org_register_address,omitempty"`
	Telephone          string                 `protobuf:"bytes,7,opt,name=telephone" json:"telephone,omitempty"`
	Email              string                 `protobuf:"bytes,8,opt,name=email" json:"email,omitempty"`
	OrgLegalPerson     string                 `protobuf:"bytes,9,opt,name=org_legal_person,json=orgLegalPerson" json:"org_legal_person,omitempty"`
	OrgnizationCode    string                 `protobuf:"bytes,10,opt,name=orgnization_code,json=orgnizationCode" json:"orgnization_code,omitempty"`
	BusinessLicenceNo  string                 `protobuf:"bytes,11,opt,name=business_licence_no,json=businessLicenceNo" json:"business_licence_no,omitempty"`
	TaxRegistrationNo  string                 `protobuf:"bytes,12,opt,name=tax_registration_no,json=taxRegistrationNo" json:"tax_registration_no,omitempty"`
	Contacts           []*ContactPerson       `protobuf:"bytes,13,rep,name=contacts" json:"contacts,omitempty"`
	SettlementAccount  *protos.OrgBankAccount `protobuf:"bytes,14,opt,name=settlement_account,json=settlementAccount" json:"settlement_account,omitempty"`
	PolicyAccount      *protos.OrgBankAccount `protobuf:"bytes,15,opt,name=policy_account,json=policyAccount" json:"policy_account,omitempty"`
	OrgFiles           []*protos.LedgerFile   `protobuf:"bytes,16,rep,name=org_files,json=orgFiles" json:"org_files,omitempty"`
	OpInfo             *protos.OperateInfo    `protobuf:"bytes,17,opt,name=op_info,json=opInfo" json:"op_info,omitempty"`
}

func (m *CommercialOrg) Reset()                    { *m = CommercialOrg{} }
func (m *CommercialOrg) String() string            { return proto.CompactTextString(m) }
func (*CommercialOrg) ProtoMessage()               {}
func (*CommercialOrg) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{7} }

func (m *CommercialOrg) GetContacts() []*ContactPerson {
	if m != nil {
		return m.Contacts
	}
	return nil
}

func (m *CommercialOrg) GetSettlementAccount() *protos.OrgBankAccount {
	if m != nil {
		return m.SettlementAccount
	}
	return nil
}

func (m *CommercialOrg) GetPolicyAccount() *protos.OrgBankAccount {
	if m != nil {
		return m.PolicyAccount
	}
	return nil
}

func (m *CommercialOrg) GetOrgFiles() []*protos.LedgerFile {
	if m != nil {
		return m.OrgFiles
	}
	return nil
}

func (m *CommercialOrg) GetOpInfo() *protos.OperateInfo {
	if m != nil {
		return m.OpInfo
	}
	return nil
}

func init() {
	proto.RegisterType((*UserPrivateInfo)(nil), "protos.UserPrivateInfo")
	proto.RegisterType((*ContactPerson)(nil), "protos.ContactPerson")
	proto.RegisterType((*MultiPartiesInfo)(nil), "protos.MultiPartiesInfo")
	proto.RegisterType((*TwoPartiesInfo)(nil), "protos.TwoPartiesInfo")
	proto.RegisterType((*MultiPInfos)(nil), "protos.MultiPInfos")
	proto.RegisterType((*TwoPInfos)(nil), "protos.TwoPInfos")
	proto.RegisterType((*UserInfo)(nil), "protos.UserInfo")
	proto.RegisterType((*CommercialOrg)(nil), "protos.CommercialOrg")
}

func init() { proto.RegisterFile("configuration/appconfig.proto", fileDescriptor0) }

var fileDescriptor0 = []byte{
	// 892 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x09, 0x6e, 0x88, 0x02, 0xff, 0x9c, 0x55, 0xcd, 0x6e, 0x1b, 0x37,
	0x10, 0x86, 0xe3, 0x1f, 0x69, 0x47, 0xfe, 0xa5, 0xd3, 0x74, 0xdb, 0x34, 0x88, 0x21, 0xe4, 0xe0,
	0x02, 0xa9, 0x94, 0xa6, 0x40, 0xd1, 0x1e, 0x72, 0x48, 0x8c, 0x14, 0x30, 0xea, 0xda, 0xee, 0x22,
	0xb9, 0xf4, 0xb2, 0xa0, 0xb8, 0xa3, 0x35, 0x21, 0x2e, 0x49, 0x90, 0xdc, 0x4a, 0xf2, 0x13, 0xf4,
	0x0d, 0x7a, 0xeb, 0x83, 0xf4, 0xe9, 0x0a, 0x92, 0xbb, 0xb2, 0x64, 0xd8, 0x46, 0x93, 0xd3, 0x2e,
	0x67, 0xbe, 0x19, 0x0e, 0x3f, 0xce, 0x7c, 0x84, 0x67, 0x4c, 0xc9, 0x31, 0x2f, 0x6b, 0x43, 0x1d,
	0x57, 0x72, 0x48, 0xb5, 0x8e, 0x86, 0x81, 0x36, 0xca, 0x29, 0xb2, 0x15, 0x3e, 0xf6, 0xeb, 0x43,
	0xa6, 0xaa, 0x4a, 0xc9, 0x61, 0xfc, 0x44, 0x67, 0xff, 0xaf, 0x75, 0xd8, 0xfb, 0x68, 0xd1, 0x5c,
	0x1a, 0xfe, 0x27, 0x75, 0x78, 0x2a, 0xc7, 0x8a, 0x7c, 0x09, 0x9d, 0xda, 0xa2, 0xc9, 0x79, 0x91,
	0xae, 0x1d, 0xad, 0x1d, 0x27, 0xd9, 0x96, 0x5f, 0x9e, 0x16, 0xe4, 0x19, 0x80, 0xae, 0x47, 0x82,
	0xb3, 0x7c, 0x82, 0xf3, 0xf4, 0x51, 0xf0, 0x25, 0xd1, 0xf2, 0x2b, 0xce, 0xc9, 0x73, 0xe8, 0xe9,
	0x98, 0x26, 0xf8, 0xd7, 0x83, 0x1f, 0x1a, 0x93, 0x07, 0xf4, 0x61, 0xc7, 0x16, 0x93, 0x3c, 0x24,
	0x97, 0xb4, 0xc2, 0x74, 0x23, 0x40, 0x7a, 0xb6, 0x98, 0xf8, 0x1a, 0xce, 0x69, 0x85, 0xe4, 0x08,
	0xb6, 0x17, 0x18, 0x3d, 0x2d, 0xd2, 0xcd, 0x98, 0xa5, 0x81, 0x5c, 0x4e, 0x0b, 0xf2, 0x14, 0x12,
	0x8f, 0x40, 0x86, 0xc6, 0xa5, 0x5b, 0xc1, 0xdd, 0xb5, 0xc5, 0xe4, 0xbd, 0x5f, 0x93, 0x1f, 0xa1,
	0x37, 0xaa, 0xaf, 0x73, 0xa6, 0xa4, 0xa3, 0xcc, 0xa5, 0x9d, 0xa3, 0xb5, 0xe3, 0xde, 0xeb, 0x2f,
	0xe2, 0x61, 0xed, 0xe0, 0x24, 0x9a, 0x2f, 0xd1, 0x58, 0x25, 0x33, 0x18, 0xd5, 0xd7, 0x8d, 0x85,
	0xfc, 0x04, 0xdb, 0x0e, 0xd9, 0xd5, 0x22, 0xb0, 0xfb, 0x50, 0x60, 0xcf, 0x43, 0xdb, 0xc8, 0x63,
	0xd8, 0xa7, 0x5a, 0xe7, 0x8c, 0x0a, 0x31, 0xa2, 0x6c, 0x92, 0xd7, 0x46, 0xa4, 0x49, 0xa8, 0x6a,
	0x97, 0x6a, 0x7d, 0xd2, 0x98, 0x3f, 0x1a, 0xe1, 0xe9, 0xb3, 0xc8, 0x6a, 0x83, 0x39, 0xd7, 0x36,
	0x85, 0x48, 0x5f, 0xb4, 0x9c, 0x6a, 0xdb, 0xff, 0x1d, 0x76, 0x56, 0xb6, 0x21, 0x04, 0x36, 0x02,
	0x4b, 0xf1, 0x12, 0xc2, 0x3f, 0x79, 0x02, 0x5b, 0x95, 0x1a, 0x71, 0x81, 0x0d, 0xfd, 0xcd, 0x8a,
	0x3c, 0x86, 0x4d, 0xac, 0x28, 0x17, 0x0d, 0xeb, 0x71, 0xd1, 0xff, 0xe7, 0x11, 0xec, 0xff, 0x56,
	0x0b, 0xc7, 0x2f, 0xa9, 0x71, 0x1c, 0x6d, 0xb8, 0xde, 0xa7, 0x90, 0x08, 0x2c, 0xca, 0xe5, 0x0b,
	0xee, 0x46, 0xc3, 0x69, 0x41, 0x5e, 0x01, 0xa0, 0x74, 0x68, 0xb4, 0xe1, 0x36, 0xee, 0xd1, 0x7b,
	0xbd, 0xdf, 0xb2, 0xe0, 0x6f, 0xc0, 0xa7, 0xc8, 0x96, 0x30, 0x64, 0x00, 0xc9, 0x98, 0x32, 0xa7,
	0x0c, 0x97, 0x65, 0xd8, 0xfd, 0xae, 0x80, 0x1b, 0x08, 0x79, 0x09, 0x5d, 0x5b, 0x6b, 0x2d, 0x38,
	0x9a, 0x70, 0xff, 0x77, 0xc1, 0x17, 0x08, 0x32, 0x04, 0x18, 0x51, 0x39, 0xc9, 0xb9, 0x1c, 0x2b,
	0x9b, 0x6e, 0x1e, 0xad, 0xdf, 0x9d, 0xde, 0x63, 0xfc, 0x9f, 0x25, 0x2f, 0xa1, 0xa3, 0x74, 0x80,
	0xa7, 0x07, 0x21, 0xfb, 0x61, 0x8b, 0xbe, 0xd0, 0x68, 0x9a, 0x16, 0xcf, 0xb6, 0x94, 0xf6, 0xdf,
	0xfe, 0xbf, 0x6b, 0xb0, 0xfb, 0x61, 0xaa, 0xfe, 0x37, 0x3d, 0xdf, 0x41, 0xb2, 0x28, 0xe7, 0x5e,
	0x76, 0xba, 0x6d, 0x35, 0x9f, 0xc1, 0xcd, 0xa7, 0x14, 0xff, 0x06, 0x7a, 0xf1, 0x72, 0xe3, 0xc9,
	0x07, 0xb0, 0x19, 0x59, 0x5a, 0x0b, 0x2c, 0xa5, 0x6d, 0xe8, 0xed, 0x06, 0xc8, 0x22, 0xac, 0xff,
	0x33, 0x24, 0xfe, 0xe8, 0x2d, 0x6d, 0x2b, 0xc1, 0x4f, 0xda, 0xe0, 0x55, 0x72, 0xda, 0xd0, 0x19,
	0x74, 0xdb, 0xf2, 0xef, 0x57, 0x8b, 0xaf, 0xa0, 0xeb, 0x07, 0x23, 0xb4, 0x70, 0x6c, 0xd6, 0x0e,
	0xd5, 0x3a, 0x0c, 0x79, 0xe3, 0x72, 0x73, 0x8d, 0x4d, 0xc3, 0x7a, 0xd7, 0x87, 0xb9, 0xc6, 0x5b,
	0x1a, 0xb3, 0x71, 0x4b, 0x63, 0xfa, 0x7f, 0x6f, 0xfa, 0x29, 0xa9, 0x2a, 0x34, 0x8c, 0x53, 0x71,
	0x61, 0xca, 0x07, 0xf7, 0x57, 0xa6, 0x5c, 0x16, 0x9a, 0x8e, 0x32, 0x65, 0xd8, 0xff, 0x39, 0xf4,
	0xbc, 0x8b, 0x16, 0x85, 0x41, 0x6b, 0x5b, 0x8d, 0x51, 0xa6, 0x7c, 0x1b, 0x2d, 0xe4, 0x15, 0x3c,
	0xf6, 0x00, 0x83, 0x25, 0xb7, 0x0e, 0xcd, 0x02, 0x19, 0xe5, 0x86, 0x28, 0x53, 0x66, 0x8d, 0xab,
	0x8d, 0xf8, 0x06, 0x12, 0x87, 0x02, 0xf5, 0x95, 0x92, 0x18, 0x64, 0x27, 0xc9, 0x6e, 0x0c, 0x37,
	0xe3, 0xd9, 0x5d, 0x1a, 0x4f, 0x2f, 0x1d, 0x7e, 0x17, 0x81, 0x25, 0x15, 0xb9, 0x0e, 0x43, 0xdf,
	0x4a, 0x87, 0x32, 0xe5, 0x99, 0x37, 0x37, 0x52, 0xf0, 0x6d, 0x40, 0x4a, 0x7e, 0x1d, 0x24, 0x3e,
	0x67, 0xaa, 0xc0, 0x46, 0x40, 0xf6, 0x96, 0xec, 0x27, 0xaa, 0xf0, 0xf3, 0x78, 0x38, 0xaa, 0x2d,
	0x97, 0x68, 0x6d, 0x2e, 0x38, 0x43, 0xc9, 0x30, 0x97, 0x2a, 0xed, 0x05, 0xf4, 0x41, 0xeb, 0x3a,
	0x8b, 0x9e, 0x73, 0xdf, 0xa3, 0x87, 0x8e, 0xce, 0x9a, 0xa3, 0xc6, 0x27, 0xc4, 0xe3, 0xb7, 0x23,
	0xde, 0xd1, 0x59, 0xb6, 0xe4, 0x39, 0x57, 0xe4, 0x7b, 0xe8, 0x36, 0x22, 0x69, 0xd3, 0x9d, 0xd0,
	0x2c, 0xf7, 0xa8, 0xe4, 0x02, 0x46, 0xde, 0x03, 0xb1, 0xe8, 0x9c, 0xc0, 0x0a, 0xa5, 0xcb, 0x29,
	0x63, 0xaa, 0x96, 0x2e, 0xdd, 0x0d, 0x1d, 0xbe, 0xe8, 0xb4, 0x0b, 0x53, 0xbe, 0xa3, 0x72, 0xf2,
	0x36, 0x7a, 0xb3, 0x83, 0x9b, 0x88, 0xc6, 0x44, 0xde, 0xc0, 0xae, 0x56, 0x82, 0xb3, 0xf9, 0x22,
	0xc5, 0xde, 0x83, 0x29, 0x76, 0x22, 0xba, 0x0d, 0x1f, 0x42, 0xe2, 0xd9, 0x1e, 0x73, 0x81, 0x36,
	0xdd, 0x0f, 0x95, 0x93, 0x36, 0xf2, 0x2c, 0x0c, 0xf8, 0x2f, 0x5c, 0x60, 0xe6, 0x9b, 0xc6, 0xff,
	0x7c, 0xa2, 0x94, 0xbc, 0x3b, 0x83, 0x17, 0x4c, 0x55, 0x83, 0x19, 0xa7, 0xb2, 0x94, 0x74, 0x30,
	0x9d, 0x4e, 0x5b, 0xf4, 0xca, 0xf3, 0xfc, 0xc7, 0x8b, 0x92, 0xbb, 0xab, 0x7a, 0x34, 0x60, 0xaa,
	0x1a, 0xce, 0x24, 0x63, 0xc3, 0x88, 0x1a, 0xae, 0xa0, 0x46, 0xf1, 0xd1, 0xfe, 0xe1, 0xbf, 0x00,
	0x00, 0x00, 0xff, 0xff, 0x9e, 0xbb, 0x33, 0x9e, 0xdc, 0x07, 0x00, 0x00,
}
