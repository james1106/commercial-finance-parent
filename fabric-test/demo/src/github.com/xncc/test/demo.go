package main

import (
	"fmt"
	"github.com/golang/protobuf/proto"
	"github.com/hyperledger/commercial-finance/fabric-test/demo/src/github.com/xncc/protos/common"
	"io/ioutil"
	"log"
	"reflect"
)

//定义注册结构map
type registerStructMaps map[string]reflect.Type

//根据name初始化结构
//在这里根据结构的成员注解进行DI注入，这里没有实现，只是简单都初始化
func (rsm registerStructMaps) New(name string) (c interface{}, err error) {
	if v, ok := rsm[name]; ok {
		c = reflect.New(v).Interface()
	} else {
		err = fmt.Errorf("not found %s struct", name)
	}
	return
}

//根据名字注册实例
func (rsm registerStructMaps) Register(name string, c interface{}) {
	rsm[name] = reflect.TypeOf(c).Elem()
}

type Test struct {
	value string
}

func (test *Test) SetValue(value string) {
	test.value = value
}
func (test *Test) Print() {
	log.Println(test.value)
}
func main() {
	//result := &common.ChainCodeResponse{
	//	Code:    1000,
	//	Message: "aaaa|ssss|eeer",
	//}
	//out, err := proto.Marshal(result)
	//if err != nil {
	//	log.Fatalln("Failed to encode address book:", err)
	//}
	//
	//if err := ioutil.WriteFile("Response", out, 0644); err != nil {
	//	log.Fatalln("Failed to write address book:", err)
	//}
	//fmt.Println("--------------------------")

	rsm := registerStructMaps{}
	////注册test
	rsm.Register("ChainCodeResponse", &common.ChainCodeResponse{})
	////获取新的test的interface
	chainCodeResponse, _ := rsm.New("ChainCodeResponse")
	result := chainCodeResponse.(*common.ChainCodeResponse)
	in, err := ioutil.ReadFile("Response")
	if err != nil {
		log.Fatalln("Failed to read file:", err)
	}

	if err := proto.Unmarshal(in, result); err != nil {
		log.Fatalln("Failed to parse address book:", err)
	}
	fmt.Println(result.String())
	result.Code = 1223
	fmt.Println(result.String())
	//test22, _ := rsm.New("test")
	////因为 test11 和 test22都是interface{},必须转换为*Test
	//test1 := test11.(*Test)
	//test2 := test22.(*Test)
	//test1.SetValue("aaa")
	//test2.SetValue("bbb")
	//test1.Print()
	//test2.Print()
}
