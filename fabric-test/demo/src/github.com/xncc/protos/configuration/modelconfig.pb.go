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

// ======================================================三方关系配置信息=======================================
// 机构信息，在入三方账本时放到账本中公开
type CommercialOrg struct {
	AppId   string `protobuf:"bytes,1,opt,name=appId" json:"appId,omitempty"`
	AppName string `protobuf:"bytes,2,opt,name=appName" json:"appName,omitempty"`
	AppType string `protobuf:"bytes,3,opt,name=appType" json:"appType,omitempty"`
	// 如果三证合一只需要填写统一社会信用代码
	OrgCode                      string `protobuf:"bytes,4,opt,name=org_code,json=orgCode" json:"org_code,omitempty"`
	RegistedNo                   string `protobuf:"bytes,5,opt,name=registed_no,json=registedNo" json:"registed_no,omitempty"`
	TaxRegistrationCertificateNo string `protobuf:"bytes,6,opt,name=tax_registration_certificate_no,json=taxRegistrationCertificateNo" json:"tax_registration_certificate_no,omitempty"`
	// 机构基本信息
	OrgCorporate    string                            `protobuf:"bytes,7,opt,name=org_corporate,json=orgCorporate" json:"org_corporate,omitempty"`
	ContactPerson   []*CommercialOrg_OrgContactPerson `protobuf:"bytes,8,rep,name=contact_person,json=contactPerson" json:"contact_person,omitempty"`
	Introduction    string                            `protobuf:"bytes,9,opt,name=introduction" json:"introduction,omitempty"`
	RegisterAddress string                            `protobuf:"bytes,10,opt,name=register_address,json=registerAddress" json:"register_address,omitempty"`
	CurrentAddress  string                            `protobuf:"bytes,11,opt,name=current_address,json=currentAddress" json:"current_address,omitempty"`
	Telephone       string                            `protobuf:"bytes,12,opt,name=telephone" json:"telephone,omitempty"`
	Email           string                            `protobuf:"bytes,13,opt,name=email" json:"email,omitempty"`
	OrgCategory     string                            `protobuf:"bytes,14,opt,name=org_category,json=orgCategory" json:"org_category,omitempty"`
	// 三方账本时:
	//           供应商维护两个账户
	//           核心企业维护一个还款账户到结算账户
	//           保理公司维护两个账户:一个放款账户到结算账户,一个收还款账户
	// 双方账本时：
	//           保理公司维护两个账户
	//           保理公司维护两个账户:一个放款账户到结算账户,一个收还款账户
	SettlementAccount *protos.OrgBankAccount `protobuf:"bytes,15,opt,name=settlement_account,json=settlementAccount" json:"settlement_account,omitempty"`
	PolicyAccount     *protos.OrgBankAccount `protobuf:"bytes,16,opt,name=policy_account,json=policyAccount" json:"policy_account,omitempty"`
	OrgFiles          []*protos.LedgerFile   `protobuf:"bytes,17,rep,name=org_files,json=orgFiles" json:"org_files,omitempty"`
	OperateInfo       *protos.OperateInfo    `protobuf:"bytes,18,opt,name=operate_info,json=operateInfo" json:"operate_info,omitempty"`
}

func (m *CommercialOrg) Reset()                    { *m = CommercialOrg{} }
func (m *CommercialOrg) String() string            { return proto.CompactTextString(m) }
func (*CommercialOrg) ProtoMessage()               {}
func (*CommercialOrg) Descriptor() ([]byte, []int) { return fileDescriptor3, []int{0} }

func (m *CommercialOrg) GetAppId() string {
	if m != nil {
		return m.AppId
	}
	return ""
}

func (m *CommercialOrg) GetAppName() string {
	if m != nil {
		return m.AppName
	}
	return ""
}

func (m *CommercialOrg) GetAppType() string {
	if m != nil {
		return m.AppType
	}
	return ""
}

func (m *CommercialOrg) GetOrgCode() string {
	if m != nil {
		return m.OrgCode
	}
	return ""
}

func (m *CommercialOrg) GetRegistedNo() string {
	if m != nil {
		return m.RegistedNo
	}
	return ""
}

func (m *CommercialOrg) GetTaxRegistrationCertificateNo() string {
	if m != nil {
		return m.TaxRegistrationCertificateNo
	}
	return ""
}

func (m *CommercialOrg) GetOrgCorporate() string {
	if m != nil {
		return m.OrgCorporate
	}
	return ""
}

func (m *CommercialOrg) GetContactPerson() []*CommercialOrg_OrgContactPerson {
	if m != nil {
		return m.ContactPerson
	}
	return nil
}

func (m *CommercialOrg) GetIntroduction() string {
	if m != nil {
		return m.Introduction
	}
	return ""
}

func (m *CommercialOrg) GetRegisterAddress() string {
	if m != nil {
		return m.RegisterAddress
	}
	return ""
}

func (m *CommercialOrg) GetCurrentAddress() string {
	if m != nil {
		return m.CurrentAddress
	}
	return ""
}

func (m *CommercialOrg) GetTelephone() string {
	if m != nil {
		return m.Telephone
	}
	return ""
}

func (m *CommercialOrg) GetEmail() string {
	if m != nil {
		return m.Email
	}
	return ""
}

func (m *CommercialOrg) GetOrgCategory() string {
	if m != nil {
		return m.OrgCategory
	}
	return ""
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

func (m *CommercialOrg) GetOperateInfo() *protos.OperateInfo {
	if m != nil {
		return m.OperateInfo
	}
	return nil
}

// 机构联系人
type CommercialOrg_OrgContactPerson struct {
	Name      string `protobuf:"bytes,1,opt,name=name" json:"name,omitempty"`
	Telephone string `protobuf:"bytes,2,opt,name=telephone" json:"telephone,omitempty"`
	Email     string `protobuf:"bytes,3,opt,name=email" json:"email,omitempty"`
}

func (m *CommercialOrg_OrgContactPerson) Reset()         { *m = CommercialOrg_OrgContactPerson{} }
func (m *CommercialOrg_OrgContactPerson) String() string { return proto.CompactTextString(m) }
func (*CommercialOrg_OrgContactPerson) ProtoMessage()    {}
func (*CommercialOrg_OrgContactPerson) Descriptor() ([]byte, []int) {
	return fileDescriptor3, []int{0, 0}
}

func (m *CommercialOrg_OrgContactPerson) GetName() string {
	if m != nil {
		return m.Name
	}
	return ""
}

func (m *CommercialOrg_OrgContactPerson) GetTelephone() string {
	if m != nil {
		return m.Telephone
	}
	return ""
}

func (m *CommercialOrg_OrgContactPerson) GetEmail() string {
	if m != nil {
		return m.Email
	}
	return ""
}

// 三方机构关系上链数据定义
type FinancingRelation struct {
	LedgerId string `protobuf:"bytes,1,opt,name=ledger_id,json=ledgerId" json:"ledger_id,omitempty"`
	// 三方机构的信息
	// 供应商需要在扩展字段中保留供应商扩展信息
	Supplier   *CommercialOrg `protobuf:"bytes,2,opt,name=supplier" json:"supplier,omitempty"`
	Enterprise *CommercialOrg `protobuf:"bytes,3,opt,name=enterprise" json:"enterprise,omitempty"`
	BankFactor *CommercialOrg `protobuf:"bytes,4,opt,name=bank_factor,json=bankFactor" json:"bank_factor,omitempty"`
	// 三方机构关系确立时可以设定默认的融资利率
	Rate              int64                `protobuf:"varint,5,opt,name=rate" json:"rate,omitempty"`
	OrderFilesEncrypt []*protos.LedgerFile `protobuf:"bytes,6,rep,name=order_files_encrypt,json=orderFilesEncrypt" json:"order_files_encrypt,omitempty"`
	// 三方机构关系确立的相关合同文件
	OrderFiles []*protos.LedgerFile `protobuf:"bytes,7,rep,name=order_files,json=orderFiles" json:"order_files,omitempty"`
	// 保理向银行融资贷款时,勾选此三方的融资订单为质押
	// 银行需要拥有此三方账本,此字段只是备注记录,不做他用
	Banks    []*protos.AppVo `protobuf:"bytes,8,rep,name=banks" json:"banks,omitempty"`
	StepFlow *ContractFlow   `protobuf:"bytes,9,opt,name=step_flow,json=stepFlow" json:"step_flow,omitempty"`
}

func (m *FinancingRelation) Reset()                    { *m = FinancingRelation{} }
func (m *FinancingRelation) String() string            { return proto.CompactTextString(m) }
func (*FinancingRelation) ProtoMessage()               {}
func (*FinancingRelation) Descriptor() ([]byte, []int) { return fileDescriptor3, []int{1} }

func (m *FinancingRelation) GetLedgerId() string {
	if m != nil {
		return m.LedgerId
	}
	return ""
}

func (m *FinancingRelation) GetSupplier() *CommercialOrg {
	if m != nil {
		return m.Supplier
	}
	return nil
}

func (m *FinancingRelation) GetEnterprise() *CommercialOrg {
	if m != nil {
		return m.Enterprise
	}
	return nil
}

func (m *FinancingRelation) GetBankFactor() *CommercialOrg {
	if m != nil {
		return m.BankFactor
	}
	return nil
}

func (m *FinancingRelation) GetRate() int64 {
	if m != nil {
		return m.Rate
	}
	return 0
}

func (m *FinancingRelation) GetOrderFilesEncrypt() []*protos.LedgerFile {
	if m != nil {
		return m.OrderFilesEncrypt
	}
	return nil
}

func (m *FinancingRelation) GetOrderFiles() []*protos.LedgerFile {
	if m != nil {
		return m.OrderFiles
	}
	return nil
}

func (m *FinancingRelation) GetBanks() []*protos.AppVo {
	if m != nil {
		return m.Banks
	}
	return nil
}

func (m *FinancingRelation) GetStepFlow() *ContractFlow {
	if m != nil {
		return m.StepFlow
	}
	return nil
}

// 银行保理双方关系上链数据定义
type BankLoanRelation struct {
	LedgerId string `protobuf:"bytes,1,opt,name=ledger_id,json=ledgerId" json:"ledger_id,omitempty"`
	// 银行和保理双方机构的信息
	BankFactor *CommercialOrg `protobuf:"bytes,2,opt,name=bank_factor,json=bankFactor" json:"bank_factor,omitempty"`
	BankInfo   *CommercialOrg `protobuf:"bytes,3,opt,name=bank_info,json=bankInfo" json:"bank_info,omitempty"`
	// 双方机构关系确立时可以设定默认的融资利率
	Rate int64 `protobuf:"varint,4,opt,name=rate" json:"rate,omitempty"`
	// 双方机构关系确立的相关合同文件
	OrderFiles []*protos.LedgerFile `protobuf:"bytes,5,rep,name=order_files,json=orderFiles" json:"order_files,omitempty"`
	StepFlow   *ContractFlow        `protobuf:"bytes,6,opt,name=step_flow,json=stepFlow" json:"step_flow,omitempty"`
}

func (m *BankLoanRelation) Reset()                    { *m = BankLoanRelation{} }
func (m *BankLoanRelation) String() string            { return proto.CompactTextString(m) }
func (*BankLoanRelation) ProtoMessage()               {}
func (*BankLoanRelation) Descriptor() ([]byte, []int) { return fileDescriptor3, []int{2} }

func (m *BankLoanRelation) GetLedgerId() string {
	if m != nil {
		return m.LedgerId
	}
	return ""
}

func (m *BankLoanRelation) GetBankFactor() *CommercialOrg {
	if m != nil {
		return m.BankFactor
	}
	return nil
}

func (m *BankLoanRelation) GetBankInfo() *CommercialOrg {
	if m != nil {
		return m.BankInfo
	}
	return nil
}

func (m *BankLoanRelation) GetRate() int64 {
	if m != nil {
		return m.Rate
	}
	return 0
}

func (m *BankLoanRelation) GetOrderFiles() []*protos.LedgerFile {
	if m != nil {
		return m.OrderFiles
	}
	return nil
}

func (m *BankLoanRelation) GetStepFlow() *ContractFlow {
	if m != nil {
		return m.StepFlow
	}
	return nil
}

func init() {
	proto.RegisterType((*CommercialOrg)(nil), "protos.CommercialOrg")
	proto.RegisterType((*CommercialOrg_OrgContactPerson)(nil), "protos.CommercialOrg.OrgContactPerson")
	proto.RegisterType((*FinancingRelation)(nil), "protos.FinancingRelation")
	proto.RegisterType((*BankLoanRelation)(nil), "protos.BankLoanRelation")
}

func init() { proto.RegisterFile("configuration/modelconfig.proto", fileDescriptor3) }

var fileDescriptor3 = []byte{
	// 790 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x09, 0x6e, 0x88, 0x02, 0xff, 0x8c, 0x55, 0xdd, 0x4e, 0x23, 0x37,
	0x14, 0x16, 0x04, 0x42, 0x72, 0x26, 0xe1, 0xc7, 0x6c, 0x2b, 0x97, 0xae, 0x04, 0x65, 0x57, 0x2d,
	0xbd, 0x49, 0xb4, 0x59, 0x75, 0xef, 0x7a, 0x01, 0x08, 0x24, 0x24, 0x0a, 0xd5, 0xa8, 0xea, 0xc5,
	0xde, 0x8c, 0x8c, 0xe7, 0x64, 0xd6, 0xda, 0x19, 0x1f, 0xcb, 0xe3, 0x28, 0xf0, 0x24, 0x7d, 0x94,
	0xbe, 0x53, 0x9f, 0xa2, 0xb2, 0x9d, 0xc9, 0x0f, 0xda, 0x20, 0xae, 0xc6, 0xfe, 0xce, 0xf7, 0x8d,
	0x8f, 0xbf, 0x73, 0xce, 0x0c, 0x1c, 0x4b, 0xd2, 0x63, 0x55, 0x4c, 0xac, 0x70, 0x8a, 0xf4, 0xb0,
	0xa2, 0x1c, 0xcb, 0x08, 0x0d, 0x8c, 0x25, 0x47, 0xac, 0x1d, 0x1e, 0xf5, 0xd1, 0xa1, 0xa4, 0xaa,
	0x22, 0x3d, 0x8c, 0x8f, 0x18, 0x3c, 0x3a, 0x59, 0x55, 0x4b, 0xd2, 0xce, 0x0a, 0xe9, 0xc6, 0x25,
	0x4d, 0x23, 0xe3, 0xf4, 0xbf, 0x36, 0xf4, 0x2f, 0xa9, 0xaa, 0xd0, 0x4a, 0x25, 0xca, 0x7b, 0x5b,
	0xb0, 0x37, 0xb0, 0x2d, 0x8c, 0xb9, 0xc9, 0xf9, 0xc6, 0xc9, 0xc6, 0x59, 0x37, 0x8d, 0x1b, 0xc6,
	0x61, 0x47, 0x18, 0x73, 0x27, 0x2a, 0xe4, 0x9b, 0x01, 0x6f, 0xb6, 0xb3, 0xc8, 0x5f, 0x4f, 0x06,
	0x79, 0x6b, 0x1e, 0xf1, 0x5b, 0xf6, 0x03, 0x74, 0xc8, 0x16, 0x99, 0xa4, 0x1c, 0xf9, 0x56, 0x0c,
	0x91, 0x2d, 0x2e, 0x29, 0x47, 0x76, 0x0c, 0x89, 0xc5, 0x42, 0xd5, 0x0e, 0xf3, 0x4c, 0x13, 0xdf,
	0x0e, 0x51, 0x68, 0xa0, 0x3b, 0x62, 0x57, 0x70, 0xec, 0xc4, 0x63, 0x16, 0x91, 0x98, 0x7e, 0x26,
	0xd1, 0x3a, 0x35, 0x56, 0x52, 0x38, 0xf4, 0xa2, 0x76, 0x10, 0xbd, 0x75, 0xe2, 0x31, 0x5d, 0x62,
	0x5d, 0x2e, 0x48, 0x77, 0xc4, 0xde, 0x41, 0x3f, 0xa6, 0x60, 0x0d, 0x59, 0xe1, 0x90, 0xef, 0x04,
	0x51, 0x2f, 0xe4, 0x31, 0xc3, 0xd8, 0x1f, 0xb0, 0xeb, 0x9d, 0x11, 0xd2, 0x65, 0x06, 0x6d, 0x4d,
	0x9a, 0x77, 0x4e, 0x5a, 0x67, 0xc9, 0xe8, 0xe7, 0xe8, 0x51, 0x3d, 0x58, 0x31, 0x68, 0x70, 0xef,
	0xb5, 0x81, 0xfe, 0x67, 0x60, 0xa7, 0x7d, 0xb9, 0xbc, 0x65, 0xa7, 0xd0, 0x53, 0xda, 0x59, 0xca,
	0x27, 0xd2, 0x27, 0xc4, 0xbb, 0xf1, 0xc8, 0x65, 0x8c, 0xfd, 0x0a, 0xfb, 0xb3, 0xcb, 0xda, 0x4c,
	0xe4, 0xb9, 0xc5, 0xba, 0xe6, 0x10, 0x78, 0x7b, 0x0d, 0x7e, 0x1e, 0x61, 0xf6, 0x0b, 0xec, 0xc9,
	0x89, 0xb5, 0xa8, 0xdd, 0x9c, 0x99, 0x04, 0xe6, 0xee, 0x0c, 0x6e, 0x88, 0x6f, 0xa1, 0xeb, 0xb0,
	0x44, 0xf3, 0x85, 0x34, 0xf2, 0x5e, 0xa0, 0x2c, 0x00, 0x5f, 0x56, 0xac, 0x84, 0x2a, 0x79, 0x3f,
	0x96, 0x35, 0x6c, 0xd8, 0x4f, 0xd0, 0x0b, 0xfe, 0x08, 0x87, 0x05, 0xd9, 0x27, 0xbe, 0x1b, 0x82,
	0x89, 0xb7, 0x67, 0x06, 0xb1, 0x2b, 0x60, 0x35, 0x3a, 0x57, 0x62, 0x15, 0x52, 0x90, 0x92, 0x26,
	0xda, 0xf1, 0xbd, 0x93, 0x8d, 0xb3, 0x64, 0xf4, 0x7d, 0xe3, 0xd0, 0xbd, 0x2d, 0x2e, 0x84, 0xfe,
	0x7a, 0x1e, 0xa3, 0xe9, 0xc1, 0x42, 0x31, 0x83, 0xd8, 0xef, 0xb0, 0x6b, 0xa8, 0x54, 0xf2, 0x69,
	0xfe, 0x8a, 0xfd, 0x17, 0x5f, 0xd1, 0x8f, 0xec, 0x46, 0x3e, 0x84, 0xae, 0x4f, 0x74, 0xac, 0x4a,
	0xac, 0xf9, 0x41, 0x28, 0x0f, 0x6b, 0x94, 0xb7, 0x98, 0x17, 0x68, 0xaf, 0x55, 0x89, 0xa9, 0x6f,
	0x38, 0xbf, 0xa8, 0xd9, 0x27, 0xe8, 0x91, 0x41, 0x5f, 0xdf, 0x4c, 0xe9, 0x31, 0x71, 0x16, 0x4e,
	0x3b, 0x9c, 0x9f, 0x16, 0x63, 0x37, 0x7a, 0x4c, 0x69, 0x42, 0x8b, 0xcd, 0xd1, 0x67, 0xd8, 0x7f,
	0x5e, 0x60, 0xc6, 0x60, 0x4b, 0xfb, 0xce, 0x8f, 0x13, 0x11, 0xd6, 0xab, 0x6e, 0x6f, 0xae, 0x75,
	0xbb, 0xb5, 0xe4, 0xf6, 0xe9, 0xbf, 0x2d, 0x38, 0xb8, 0x56, 0x5a, 0x68, 0xa9, 0x74, 0x91, 0x62,
	0x19, 0x1a, 0x96, 0xfd, 0x08, 0xdd, 0x32, 0xdc, 0x20, 0x53, 0xcd, 0xd0, 0x75, 0x22, 0x70, 0x93,
	0xb3, 0x0f, 0xd0, 0xa9, 0x27, 0xc6, 0x94, 0x0a, 0x6d, 0x38, 0x25, 0x19, 0x7d, 0xf7, 0xcd, 0xae,
	0x4c, 0xe7, 0x34, 0xf6, 0x1b, 0x00, 0x6a, 0x87, 0xd6, 0x58, 0x55, 0xc7, 0x99, 0x5c, 0x2b, 0x5a,
	0x22, 0xb2, 0x4f, 0x90, 0x3c, 0x08, 0xfd, 0x35, 0x1b, 0x0b, 0xe9, 0xc8, 0x86, 0x81, 0x5d, 0xaf,
	0xf3, 0xcc, 0xeb, 0x40, 0xf4, 0xe6, 0x84, 0xc9, 0xf2, 0x33, 0xdc, 0x4a, 0xc3, 0x9a, 0x5d, 0xc0,
	0x21, 0xd9, 0x1c, 0x6d, 0xac, 0x57, 0x86, 0x5a, 0xda, 0x27, 0xe3, 0x78, 0x7b, 0x6d, 0xdd, 0x0e,
	0x02, 0x3d, 0x54, 0xee, 0x2a, 0x92, 0xd9, 0x47, 0x48, 0x96, 0xde, 0xc1, 0x77, 0xd6, 0x6a, 0x61,
	0xa1, 0x65, 0xef, 0x60, 0xdb, 0xa7, 0x56, 0xcf, 0x26, 0xb8, 0xdf, 0xd0, 0xcf, 0x8d, 0xf9, 0x9b,
	0xd2, 0x18, 0x63, 0x1f, 0xa0, 0x5b, 0x3b, 0x34, 0x99, 0xff, 0x0c, 0x86, 0xe9, 0x4c, 0x46, 0x6f,
	0x16, 0xf7, 0x8c, 0x9f, 0xc8, 0xeb, 0x92, 0xa6, 0x69, 0xc7, 0xd3, 0xfc, 0xea, 0xf4, 0x9f, 0x4d,
	0xd8, 0xf7, 0xdd, 0x79, 0x4b, 0x42, 0xbf, 0xae, 0x70, 0xcf, 0xec, 0xdc, 0x7c, 0xad, 0x9d, 0x23,
	0xe8, 0x06, 0x5d, 0x68, 0xda, 0x17, 0x8b, 0xd7, 0xf1, 0x3c, 0xdf, 0xb3, 0xf3, 0x12, 0x6c, 0x2d,
	0x95, 0xe0, 0x99, 0x7d, 0xdb, 0xaf, 0xb2, 0x6f, 0xc5, 0x99, 0xf6, 0x6b, 0x9c, 0xb9, 0xb8, 0x85,
	0xf7, 0x92, 0xaa, 0xc1, 0xa3, 0x12, 0xba, 0xd0, 0x62, 0x30, 0x9d, 0x4e, 0x1b, 0xc1, 0xca, 0xbf,
	0xe7, 0xf3, 0xfb, 0x42, 0xb9, 0x2f, 0x93, 0x87, 0x81, 0xa4, 0x6a, 0xf8, 0xa8, 0xa5, 0x1c, 0x46,
	0xd6, 0x70, 0x85, 0xf5, 0x10, 0xff, 0x66, 0x1f, 0xff, 0x0f, 0x00, 0x00, 0xff, 0xff, 0x33, 0x8e,
	0xe3, 0x36, 0xf7, 0x06, 0x00, 0x00,
}