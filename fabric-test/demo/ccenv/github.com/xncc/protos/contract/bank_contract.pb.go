// Code generated by protoc-gen-go.
// source: contract/bank_contract.proto
// DO NOT EDIT!

/*
Package contract is a generated protocol buffer package.

It is generated from these files:
	contract/bank_contract.proto
	contract/contract.proto
	contract/contract_data.proto
	contract/contract_request.proto
	contract/contract_status.proto

It has these top-level messages:
	BankLoanContract
	FinancingContractReview
	FinancingContract
	Invoice
	ContractFormData
	ContractMoneyCount
	ContractTransactionDetail
	ContractExcuteRequest
	FileAddRequest
	ContractAddCheckLogRequest
	ContractQueryRequest
	ContractStatus
	ContractCheckFlowInfo
	ContractCheckFlowData
	ContractStepCheckData
	CheckData
*/
package contract

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

// ========================================银行保理双方账本数据定义===========================================
// 融资申请订单
// 保理到银行贷款融资申请订单
type BankLoanContract struct {
	OrderNo                  string                     `protobuf:"bytes,1,opt,name=order_no,json=orderNo" json:"order_no,omitempty"`
	Factoring                *protos.AppVo              `protobuf:"bytes,2,opt,name=factoring" json:"factoring,omitempty"`
	BankInfo                 *protos.AppVo              `protobuf:"bytes,3,opt,name=bank_info,json=bankInfo" json:"bank_info,omitempty"`
	ContractData             *ContractFormData          `protobuf:"bytes,4,opt,name=contract_data,json=contractData" json:"contract_data,omitempty"`
	FinancingContractReviews []*FinancingContractReview `protobuf:"bytes,5,rep,name=financing_contract_reviews,json=financingContractReviews" json:"financing_contract_reviews,omitempty"`
	MoneyCount               *ContractMoneyCount        `protobuf:"bytes,6,opt,name=money_count,json=moneyCount" json:"money_count,omitempty"`
	ContractStatus           *ContractStatus            `protobuf:"bytes,7,opt,name=contract_status,json=contractStatus" json:"contract_status,omitempty"`
	OperateInfo              *protos.OperateInfo        `protobuf:"bytes,8,opt,name=operate_info,json=operateInfo" json:"operate_info,omitempty"`
}

func (m *BankLoanContract) Reset()                    { *m = BankLoanContract{} }
func (m *BankLoanContract) String() string            { return proto.CompactTextString(m) }
func (*BankLoanContract) ProtoMessage()               {}
func (*BankLoanContract) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{0} }

func (m *BankLoanContract) GetFactoring() *protos.AppVo {
	if m != nil {
		return m.Factoring
	}
	return nil
}

func (m *BankLoanContract) GetBankInfo() *protos.AppVo {
	if m != nil {
		return m.BankInfo
	}
	return nil
}

func (m *BankLoanContract) GetContractData() *ContractFormData {
	if m != nil {
		return m.ContractData
	}
	return nil
}

func (m *BankLoanContract) GetFinancingContractReviews() []*FinancingContractReview {
	if m != nil {
		return m.FinancingContractReviews
	}
	return nil
}

func (m *BankLoanContract) GetMoneyCount() *ContractMoneyCount {
	if m != nil {
		return m.MoneyCount
	}
	return nil
}

func (m *BankLoanContract) GetContractStatus() *ContractStatus {
	if m != nil {
		return m.ContractStatus
	}
	return nil
}

func (m *BankLoanContract) GetOperateInfo() *protos.OperateInfo {
	if m != nil {
		return m.OperateInfo
	}
	return nil
}

// 保理到银行贷款融资申请订单质押的供应商融资申请订单
type FinancingContractReview struct {
	FinancingOrderNo string `protobuf:"bytes,1,opt,name=financing_order_no,json=financingOrderNo" json:"financing_order_no,omitempty"`
	OrderAmount      int64  `protobuf:"varint,2,opt,name=order_amount,json=orderAmount" json:"order_amount,omitempty"`
	FinancingPeriod  int32  `protobuf:"varint,3,opt,name=financing_period,json=financingPeriod" json:"financing_period,omitempty"`
	OrderExpiredTime int64  `protobuf:"varint,4,opt,name=order_expired_time,json=orderExpiredTime" json:"order_expired_time,omitempty"`
	// 保理申请的融资金额
	ExpectAmount int64 `protobuf:"varint,5,opt,name=expect_amount,json=expectAmount" json:"expect_amount,omitempty"`
	ExpectRate   int64 `protobuf:"varint,6,opt,name=expect_rate,json=expectRate" json:"expect_rate,omitempty"`
	// 银行审批确认的融资金额
	ConfirmAmount      int64         `protobuf:"varint,7,opt,name=confirm_amount,json=confirmAmount" json:"confirm_amount,omitempty"`
	ConfirmRate        int64         `protobuf:"varint,8,opt,name=confirm_rate,json=confirmRate" json:"confirm_rate,omitempty"`
	Supplier           *protos.AppVo `protobuf:"bytes,9,opt,name=supplier" json:"supplier,omitempty"`
	Enterprise         *protos.AppVo `protobuf:"bytes,10,opt,name=enterprise" json:"enterprise,omitempty"`
	BankFactor         *protos.AppVo `protobuf:"bytes,11,opt,name=bank_factor,json=bankFactor" json:"bank_factor,omitempty"`
	ConfirmExpiredTime int64         `protobuf:"varint,12,opt,name=confirm_expired_time,json=confirmExpiredTime" json:"confirm_expired_time,omitempty"`
}

func (m *FinancingContractReview) Reset()                    { *m = FinancingContractReview{} }
func (m *FinancingContractReview) String() string            { return proto.CompactTextString(m) }
func (*FinancingContractReview) ProtoMessage()               {}
func (*FinancingContractReview) Descriptor() ([]byte, []int) { return fileDescriptor0, []int{1} }

func (m *FinancingContractReview) GetSupplier() *protos.AppVo {
	if m != nil {
		return m.Supplier
	}
	return nil
}

func (m *FinancingContractReview) GetEnterprise() *protos.AppVo {
	if m != nil {
		return m.Enterprise
	}
	return nil
}

func (m *FinancingContractReview) GetBankFactor() *protos.AppVo {
	if m != nil {
		return m.BankFactor
	}
	return nil
}

func init() {
	proto.RegisterType((*BankLoanContract)(nil), "protos.BankLoanContract")
	proto.RegisterType((*FinancingContractReview)(nil), "protos.FinancingContractReview")
}

func init() { proto.RegisterFile("contract/bank_contract.proto", fileDescriptor0) }

var fileDescriptor0 = []byte{
	// 574 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x09, 0x6e, 0x88, 0x02, 0xff, 0x74, 0x94, 0x6f, 0x6f, 0xd3, 0x3e,
	0x10, 0xc7, 0xb5, 0x5f, 0xf6, 0xa7, 0xbd, 0xa4, 0xbf, 0x4d, 0x1e, 0x02, 0x53, 0x21, 0xba, 0x0d,
	0x21, 0x6d, 0xfc, 0x49, 0xd1, 0x90, 0x78, 0x82, 0x10, 0xda, 0x06, 0x95, 0x90, 0x80, 0x21, 0x83,
	0x78, 0x80, 0x84, 0x22, 0xd7, 0x75, 0x8a, 0x35, 0x62, 0x47, 0x8e, 0x4b, 0xcb, 0xab, 0xe0, 0xfd,
	0xf1, 0x6a, 0x50, 0xce, 0x71, 0xbb, 0x96, 0xf2, 0x28, 0xb9, 0xfb, 0x7e, 0xfc, 0xf5, 0xf9, 0x2e,
	0x31, 0xdc, 0x11, 0x46, 0x3b, 0xcb, 0x85, 0xeb, 0x0f, 0xb9, 0xbe, 0xca, 0x42, 0x94, 0x96, 0xd6,
	0x38, 0x43, 0xb6, 0xf1, 0x51, 0x75, 0xf7, 0x85, 0x29, 0x0a, 0xa3, 0xfb, 0xfe, 0xe1, 0xc5, 0xee,
	0x62, 0x69, 0x78, 0xc9, 0x46, 0xdc, 0xf1, 0x46, 0xbd, 0xfb, 0xb7, 0x5a, 0x39, 0xee, 0x26, 0x95,
	0xd7, 0x8f, 0x7e, 0x47, 0xb0, 0x77, 0xce, 0xf5, 0xd5, 0x5b, 0xc3, 0xf5, 0x45, 0x43, 0x90, 0xdb,
	0xd0, 0x32, 0x76, 0x24, 0x6d, 0xa6, 0x0d, 0xdd, 0x38, 0xd8, 0x38, 0x6e, 0xb3, 0x1d, 0x8c, 0xdf,
	0x1b, 0xf2, 0x10, 0xda, 0x39, 0x17, 0xce, 0x58, 0xa5, 0xc7, 0xf4, 0xbf, 0x83, 0x8d, 0xe3, 0xf8,
	0xb4, 0xe3, 0xad, 0xaa, 0xf4, 0xac, 0x2c, 0x3f, 0x1b, 0xb6, 0xd0, 0xc9, 0x03, 0x68, 0xe3, 0x71,
	0x94, 0xce, 0x0d, 0x8d, 0xd6, 0xc1, 0xad, 0x5a, 0x7f, 0xa3, 0x73, 0x43, 0x5e, 0x40, 0x67, 0xa9,
	0x7e, 0xba, 0x89, 0x3c, 0x0d, 0x7c, 0x28, 0x6e, 0x60, 0x6c, 0xf1, 0x8a, 0x3b, 0xce, 0x92, 0x80,
	0xd7, 0x11, 0xf9, 0x0a, 0xdd, 0x5c, 0x69, 0xae, 0x85, 0xd2, 0xe3, 0x79, 0xfb, 0x32, 0x2b, 0x7f,
	0x28, 0x39, 0xad, 0xe8, 0xd6, 0x41, 0x74, 0x1c, 0x9f, 0xf6, 0x82, 0xd7, 0x20, 0x90, 0xc1, 0x94,
	0x21, 0xc7, 0x68, 0xbe, 0x5e, 0xa8, 0xc8, 0x73, 0x88, 0x0b, 0xa3, 0xe5, 0xcf, 0x4c, 0x98, 0x89,
	0x76, 0x74, 0x1b, 0x6b, 0xeb, 0xae, 0xd6, 0xf6, 0xae, 0x46, 0x2e, 0x6a, 0x82, 0x41, 0x31, 0x7f,
	0x27, 0x2f, 0x61, 0x77, 0xa5, 0xf9, 0x74, 0x07, 0x0d, 0x6e, 0xae, 0x1a, 0x7c, 0x44, 0x95, 0xfd,
	0x2f, 0x96, 0x62, 0xf2, 0x0c, 0x12, 0x53, 0x4a, 0xcb, 0x9d, 0xf4, 0xad, 0x6c, 0xe1, 0xea, 0xfd,
	0xb0, 0xfa, 0xd2, 0x6b, 0x75, 0x1b, 0x59, 0x6c, 0x16, 0xc1, 0xd1, 0xaf, 0x4d, 0xb8, 0xf5, 0x8f,
	0xb3, 0x92, 0x47, 0x40, 0x16, 0x0d, 0x5b, 0x99, 0xf6, 0xde, 0x5c, 0xb9, 0x6c, 0xc6, 0x7e, 0x08,
	0x89, 0x67, 0x78, 0x81, 0x0d, 0xa8, 0x27, 0x1f, 0xb1, 0x18, 0x73, 0x67, 0x98, 0x22, 0x27, 0xb0,
	0x58, 0x96, 0x95, 0xd2, 0x2a, 0x33, 0xc2, 0x99, 0x6f, 0xb1, 0xdd, 0x79, 0xfe, 0x03, 0xa6, 0xeb,
	0xbd, 0xbd, 0x9b, 0x9c, 0x95, 0xca, 0xca, 0x51, 0xe6, 0x54, 0x21, 0x71, 0xe0, 0x11, 0xdb, 0x43,
	0xe5, 0xb5, 0x17, 0x3e, 0xa9, 0x42, 0x92, 0x7b, 0xd0, 0x91, 0xb3, 0x52, 0x0a, 0x17, 0x36, 0xdf,
	0x42, 0x30, 0xf1, 0xc9, 0x66, 0xf7, 0x1e, 0xc4, 0x0d, 0x54, 0x9f, 0x1e, 0x07, 0x14, 0x31, 0xf0,
	0x29, 0xc6, 0x9d, 0x24, 0xf7, 0xa1, 0xee, 0x6a, 0xae, 0x6c, 0x11, 0x6c, 0x76, 0x90, 0xe9, 0x34,
	0xd9, 0xc6, 0xe7, 0x10, 0x92, 0x80, 0xa1, 0x51, 0xcb, 0x1f, 0xb4, 0xc9, 0xa1, 0xd3, 0x09, 0xb4,
	0xaa, 0x49, 0x59, 0x7e, 0x57, 0xd2, 0xd2, 0xf6, 0xda, 0x8f, 0x3a, 0xc8, 0xe4, 0x31, 0x80, 0xd4,
	0x4e, 0xda, 0xd2, 0xaa, 0x4a, 0x52, 0x58, 0x07, 0x5f, 0x03, 0x48, 0x0a, 0x31, 0xfe, 0x2f, 0xfe,
	0x0f, 0xa2, 0xf1, 0x5a, 0xbe, 0x26, 0x06, 0x08, 0x90, 0x27, 0x70, 0x23, 0x14, 0xbb, 0xd4, 0xc9,
	0x04, 0x8b, 0x26, 0x8d, 0x76, 0xad, 0x97, 0xe7, 0xe7, 0xd0, 0x13, 0xa6, 0x48, 0x67, 0x8a, 0xeb,
	0xb1, 0xe6, 0xe9, 0x74, 0x3a, 0x0d, 0xee, 0xe1, 0x93, 0xfb, 0xd2, 0x1b, 0x2b, 0xf7, 0x6d, 0x32,
	0x4c, 0x85, 0x29, 0xfa, 0x33, 0x2d, 0x44, 0xdf, 0x03, 0xf3, 0xfb, 0x63, 0xe8, 0x6f, 0xa3, 0xa7,
	0x7f, 0x02, 0x00, 0x00, 0xff, 0xff, 0x04, 0x7d, 0xcb, 0xcb, 0xb4, 0x04, 0x00, 0x00,
}
