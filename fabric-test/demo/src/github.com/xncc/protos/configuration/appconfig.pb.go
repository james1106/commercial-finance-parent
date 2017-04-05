// Code generated by protoc-gen-go.
// source: configuration/appconfig.proto
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

// ======================================================私有配置信息=======================================
// 节点信息
type NodeConfig struct {
	// 一般 情况，有多少个 peer就会有多少个 sdk， 每个 sdk 带有一个 user
	Pem         string `protobuf:"bytes,1,opt,name=pem" json:"pem,omitempty"`
	SdkCert     string `protobuf:"bytes,2,opt,name=sdkCert" json:"sdkCert,omitempty"`
	SdkUserName string `protobuf:"bytes,3,opt,name=sdkUserName" json:"sdkUserName,omitempty"`
	SdkUserPWD  string `protobuf:"bytes,4,opt,name=sdkUserPWD" json:"sdkUserPWD,omitempty"`
}

func (m *NodeConfig) Reset()                    { *m = NodeConfig{} }
func (m *NodeConfig) String() string            { return proto.CompactTextString(m) }
func (*NodeConfig) ProtoMessage()               {}
func (*NodeConfig) Descriptor() ([]byte, []int) { return fileDescriptor1, []int{0} }

func (m *NodeConfig) GetPem() string {
	if m != nil {
		return m.Pem
	}
	return ""
}

func (m *NodeConfig) GetSdkCert() string {
	if m != nil {
		return m.SdkCert
	}
	return ""
}

func (m *NodeConfig) GetSdkUserName() string {
	if m != nil {
		return m.SdkUserName
	}
	return ""
}

func (m *NodeConfig) GetSdkUserPWD() string {
	if m != nil {
		return m.SdkUserPWD
	}
	return ""
}

// APPConfig 是不公开的
type APPConfig struct {
	AppId            string              `protobuf:"bytes,1,opt,name=appId" json:"appId,omitempty"`
	AppKey           string              `protobuf:"bytes,2,opt,name=appKey" json:"appKey,omitempty"`
	AppName          string              `protobuf:"bytes,3,opt,name=appName" json:"appName,omitempty"`
	AppType          string              `protobuf:"bytes,4,opt,name=appType" json:"appType,omitempty"`
	AppCallbackUrl   string              `protobuf:"bytes,5,opt,name=appCallbackUrl" json:"appCallbackUrl,omitempty"`
	SecureIp         string              `protobuf:"bytes,6,opt,name=secureIp" json:"secureIp,omitempty"`
	NodeConfigs      []*NodeConfig       `protobuf:"bytes,7,rep,name=nodeConfigs" json:"nodeConfigs,omitempty"`
	OrgName          string              `protobuf:"bytes,8,opt,name=orgName" json:"orgName,omitempty"`
	OrgAddress       string              `protobuf:"bytes,9,opt,name=orgAddress" json:"orgAddress,omitempty"`
	BizContactName   string              `protobuf:"bytes,10,opt,name=bizContactName" json:"bizContactName,omitempty"`
	BizContactTel    string              `protobuf:"bytes,11,opt,name=bizContactTel" json:"bizContactTel,omitempty"`
	BizContaceEmail  string              `protobuf:"bytes,12,opt,name=bizContaceEmail" json:"bizContaceEmail,omitempty"`
	TechContactName  string              `protobuf:"bytes,13,opt,name=techContactName" json:"techContactName,omitempty"`
	TechContactTel   string              `protobuf:"bytes,14,opt,name=techContactTel" json:"techContactTel,omitempty"`
	TechContactEmail string              `protobuf:"bytes,15,opt,name=techContactEmail" json:"techContactEmail,omitempty"`
	OpInfo           *protos.OperateInfo `protobuf:"bytes,16,opt,name=opInfo" json:"opInfo,omitempty"`
}

func (m *APPConfig) Reset()                    { *m = APPConfig{} }
func (m *APPConfig) String() string            { return proto.CompactTextString(m) }
func (*APPConfig) ProtoMessage()               {}
func (*APPConfig) Descriptor() ([]byte, []int) { return fileDescriptor1, []int{1} }

func (m *APPConfig) GetAppId() string {
	if m != nil {
		return m.AppId
	}
	return ""
}

func (m *APPConfig) GetAppKey() string {
	if m != nil {
		return m.AppKey
	}
	return ""
}

func (m *APPConfig) GetAppName() string {
	if m != nil {
		return m.AppName
	}
	return ""
}

func (m *APPConfig) GetAppType() string {
	if m != nil {
		return m.AppType
	}
	return ""
}

func (m *APPConfig) GetAppCallbackUrl() string {
	if m != nil {
		return m.AppCallbackUrl
	}
	return ""
}

func (m *APPConfig) GetSecureIp() string {
	if m != nil {
		return m.SecureIp
	}
	return ""
}

func (m *APPConfig) GetNodeConfigs() []*NodeConfig {
	if m != nil {
		return m.NodeConfigs
	}
	return nil
}

func (m *APPConfig) GetOrgName() string {
	if m != nil {
		return m.OrgName
	}
	return ""
}

func (m *APPConfig) GetOrgAddress() string {
	if m != nil {
		return m.OrgAddress
	}
	return ""
}

func (m *APPConfig) GetBizContactName() string {
	if m != nil {
		return m.BizContactName
	}
	return ""
}

func (m *APPConfig) GetBizContactTel() string {
	if m != nil {
		return m.BizContactTel
	}
	return ""
}

func (m *APPConfig) GetBizContaceEmail() string {
	if m != nil {
		return m.BizContaceEmail
	}
	return ""
}

func (m *APPConfig) GetTechContactName() string {
	if m != nil {
		return m.TechContactName
	}
	return ""
}

func (m *APPConfig) GetTechContactTel() string {
	if m != nil {
		return m.TechContactTel
	}
	return ""
}

func (m *APPConfig) GetTechContactEmail() string {
	if m != nil {
		return m.TechContactEmail
	}
	return ""
}

func (m *APPConfig) GetOpInfo() *protos.OperateInfo {
	if m != nil {
		return m.OpInfo
	}
	return nil
}

func init() {
	proto.RegisterType((*NodeConfig)(nil), "protos.NodeConfig")
	proto.RegisterType((*APPConfig)(nil), "protos.APPConfig")
}

func init() { proto.RegisterFile("configuration/appconfig.proto", fileDescriptor1) }

var fileDescriptor1 = []byte{
	// 434 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x09, 0x6e, 0x88, 0x02, 0xff, 0x64, 0x93, 0xef, 0x6a, 0xdb, 0x30,
	0x14, 0xc5, 0xc9, 0xd2, 0xa6, 0xcd, 0xf5, 0xda, 0x06, 0x75, 0x0c, 0x51, 0xd8, 0x08, 0x25, 0x8c,
	0xb0, 0x81, 0x0d, 0xdd, 0x5e, 0xa0, 0xcb, 0xf6, 0x21, 0x6c, 0x74, 0x21, 0xb4, 0x0c, 0xf6, 0x4d,
	0x96, 0x55, 0xd7, 0xc4, 0x96, 0x2e, 0x92, 0x43, 0xda, 0x3c, 0xdf, 0x1e, 0x6c, 0xe8, 0x8f, 0x13,
	0x3b, 0xfb, 0x24, 0x9f, 0xdf, 0x3d, 0xd6, 0x39, 0x5c, 0x10, 0xbc, 0xe3, 0x4a, 0x3e, 0x16, 0xf9,
	0x5a, 0xb3, 0xba, 0x50, 0x32, 0x61, 0x88, 0x1e, 0xc4, 0xa8, 0x55, 0xad, 0xc8, 0xc0, 0x1d, 0xe6,
	0xea, 0x92, 0xab, 0xaa, 0x52, 0x32, 0xf1, 0x87, 0x1f, 0x5e, 0x6f, 0x01, 0xee, 0x54, 0x26, 0x66,
	0xee, 0x07, 0x32, 0x82, 0x3e, 0x8a, 0x8a, 0xf6, 0xc6, 0xbd, 0xe9, 0x70, 0x69, 0x3f, 0x09, 0x85,
	0x13, 0x93, 0xad, 0x66, 0x42, 0xd7, 0xf4, 0x95, 0xa3, 0x8d, 0x24, 0x63, 0x88, 0x4c, 0xb6, 0x7a,
	0x30, 0x42, 0xdf, 0xb1, 0x4a, 0xd0, 0xbe, 0x9b, 0xb6, 0x11, 0x79, 0x0f, 0x10, 0xe4, 0xe2, 0xf7,
	0x37, 0x7a, 0xe4, 0x0c, 0x2d, 0x72, 0xfd, 0xf7, 0x08, 0x86, 0xb7, 0x8b, 0x45, 0xc8, 0x7e, 0x03,
	0xc7, 0x0c, 0x71, 0x9e, 0x85, 0x74, 0x2f, 0xc8, 0x5b, 0x18, 0x30, 0xc4, 0x1f, 0xe2, 0x25, 0xc4,
	0x07, 0x65, 0x7b, 0x31, 0xc4, 0x56, 0x72, 0x23, 0xc3, 0xe4, 0xfe, 0x05, 0x45, 0x88, 0x6c, 0x24,
	0xf9, 0x00, 0xe7, 0x0c, 0x71, 0xc6, 0xca, 0x32, 0x65, 0x7c, 0xf5, 0xa0, 0x4b, 0x7a, 0xec, 0x0c,
	0x07, 0x94, 0x5c, 0xc1, 0xa9, 0x11, 0x7c, 0xad, 0xc5, 0x1c, 0xe9, 0xc0, 0x39, 0x76, 0x9a, 0x7c,
	0x81, 0x48, 0xee, 0xf6, 0x65, 0xe8, 0xc9, 0xb8, 0x3f, 0x8d, 0x6e, 0x88, 0x5f, 0xa6, 0x89, 0xf7,
	0xab, 0x5c, 0xb6, 0x6d, 0xb6, 0x93, 0xd2, 0xb9, 0x6b, 0x7b, 0xea, 0x3b, 0x05, 0x69, 0x77, 0xa4,
	0x74, 0x7e, 0x9b, 0x65, 0x5a, 0x18, 0x43, 0x87, 0x7e, 0x47, 0x7b, 0x62, 0x3b, 0xa7, 0xc5, 0x76,
	0xa6, 0x64, 0xcd, 0x78, 0xed, 0x2e, 0x00, 0xdf, 0xb9, 0x4b, 0xc9, 0x04, 0xce, 0xf6, 0xe4, 0x5e,
	0x94, 0x34, 0x72, 0xb6, 0x2e, 0x24, 0x53, 0xb8, 0xd8, 0x01, 0xf1, 0xbd, 0x62, 0x45, 0x49, 0x5f,
	0x3b, 0xdf, 0x21, 0xb6, 0xce, 0x5a, 0xf0, 0xa7, 0x76, 0xf0, 0x99, 0x77, 0x1e, 0x60, 0xdb, 0xb0,
	0x85, 0x6c, 0xf4, 0xb9, 0x6f, 0xd8, 0xa5, 0xe4, 0x23, 0x8c, 0x5a, 0xc4, 0x87, 0x5f, 0x38, 0xe7,
	0x7f, 0x9c, 0x7c, 0x82, 0x81, 0xc2, 0xb9, 0x7c, 0x54, 0x74, 0x34, 0xee, 0x4d, 0xa3, 0x9b, 0xcb,
	0x66, 0xc1, 0xbf, 0x50, 0x68, 0x56, 0x0b, 0x3b, 0x5a, 0x06, 0xcb, 0xd7, 0x9f, 0x30, 0xe1, 0xaa,
	0x8a, 0x9f, 0x0b, 0x26, 0x73, 0xc9, 0xe2, 0xcd, 0x66, 0xd3, 0xb8, 0x3b, 0xef, 0xe2, 0xcf, 0x24,
	0x2f, 0xea, 0xa7, 0x75, 0x1a, 0x73, 0x55, 0x25, 0xcf, 0x92, 0xf3, 0xc4, 0xbb, 0x92, 0x8e, 0x2b,
	0xf5, 0xaf, 0xe5, 0xf3, 0xbf, 0x00, 0x00, 0x00, 0xff, 0xff, 0x7d, 0xb7, 0x1d, 0xa7, 0x55, 0x03,
	0x00, 0x00,
}