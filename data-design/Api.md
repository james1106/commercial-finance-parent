# 香纳融资服务API文档

## 接口定义

### 融资申请数据接口
* 创建融资申请

* 发起融资申请（执行合约）  
* 审核确认通过（执行合约）
* 已完成放款 （执行合约）
* 已完成还款 (执行合约)

* 添加流程审批和确认检查项
* 添加转账记录
* 转账记录确认

* 流转申请（执行合约）

* 融资申请列表查询
* 融资申请详情查询
* 融资申请具体附件查看

#### 接口必须能参数
```json
{
  "appId": "应用ID",
  "appkey": "应用KEY",
  "version": "接口版本",
  "source": "请求来源",
  "sourceIP": "请求来源IP"
}
```

#### 创建融资申请
接口将会初始化融资申请编号并初始化融资申请数据
```json
{
  "ledgerId": "账本ID",
  // 订单数据
  "contractData": {
    "expectLoanAmount": 0, // 期望融资金额
    "expectLoanPeriod": 0, // 期望融资期限
    "expectLoanEndTime": 0, // 期望融资截止日期
    "expectLoanRate": 0 // 期望融资利率
  },
  // 附件
  "attas": [
    {
      "name": "文件名称",
      "url": "文件URL",
      "sha256": "文件sha256",
      "size": 0, // 文件大小
      "type": "文件类型",
      "data": "文件Base64编码字符串",
      "isEncrypted": false, // 是否需要加密
      "appIDs": ["appid"] // 可解密用户
    }
  ],
  // 发票数据
  "invoices": [
    {
       "invoiceNO": "发票编号",
       "invoiceAmount": 0, // 发票金额
       // 销售方
       "seller": {
         "name": "公司名称",
         "key": "机构唯一识别码",
         "address": "地址",
         "phone": "电话",
         "bankName": "银行名称",
         "bankAccount": "银行账户"
       },
       // 购买方
       "purchaser": {
         "name": "公司名称",
         "key": "机构唯一识别码",
         "address": "地址",
         "phone": "电话",
         "bankName": "银行名称",
         "bankAccount": "银行账户"
       },
       "invoiceRemark": "发票备注",
       "invoiceTime": 0, // 开票日期
       "expectLoanAmount": 0, // 发票期望融资金额
       "expectLoanRate": 0,// 发票期望融资利率
       "images": ["发票扫描图片Base64编码字符串"]
    }
  ]
}

```

#### 融资数据流程审批确认
接口记录融资申请订单数据在流程审批中更新,并新增流程审批记录和确认检查项
```json
{
  "ledgerId": "账本ID",
  "orderKey": "订单key（账本存储对应的key）",
  "orderNo": "订单编号",
  // 订单数据
  "contractData": {
    "expectLoanAmount": 0, // 期望融资总金额
    "expectLoanPeriod": 0, // 期望融资期限
    "expectLoanEndTime": 0, // 期望融资截止日期
    "expectLoanRate": 0, // 期望融资利率
    "confirmLoanRate": 0, // 确认的融资利率
    "confirmLoanAmount": 0, // 确认的融资总金额
    "confirmLoanPeriod": 0, // 确认的融资期限
    "confirmLoanEndTime": 0 //  确认的融资的截止日期
  },
  // 附件
  "attas": [
    {
      "name": "文件名称",
      "url": "文件URL",
      "sha256": "文件sha256",
      "size": 0, // 文件大小
      "type": "文件类型",
      "data": "文件Base64编码字符串",
      "isEncrypted": false, // 是否需要加密
      "appIDs": ["appid"] // 可解密用户
    }
  ],
  // 发票数据
  "invoices": [
    {
       "invoiceNO": "发票编号",
       "invoiceAmount": 0, // 发票金额
       // 销售方
       "seller": {
         "name": "公司名称",
         "key": "机构唯一识别码",
         "address": "地址",
         "phone": "电话",
         "bankName": "银行名称",
         "bankAccount": "银行账户"
       },
       // 购买方
       "purchaser": {
         "name": "公司名称",
         "key": "机构唯一识别码",
         "address": "地址",
         "phone": "电话",
         "bankName": "银行名称",
         "bankAccount": "银行账户"
       },
       "invoiceRemark": "发票备注",
       "images": ["发票扫描图片Base64编码字符串"],
       "invoiceTime": 0, // 开票日期
       "expectLoanAmount": 0, // 发票期望融资金额
       "expectLoanRate": 0,// 发票期望融资利率
       "confirmLoanAmount": 0, // 发票确认的融资金额
       "confirmLoanRate": 0, // 发票确认的融资利率
       "confirmPayTime": 0, // 保理融资发放日期(unix时间戳)
       "confirmReturnTime": 0, // 应收账款还款日期(unix时间戳)
       "confirmEndTime": 0, // 保理融资到期日期(unix时间戳)
       "confirmRateDesc": "利息计收方式",
       "confirmUrgeReturnDesc": "应收账款催收方式"
    }
  ],
  // 流程审批记录
  "checkFlow": [
    {
      "checkUserName": "审核员名称",
      "checkUserMobile": "审核员手机号码",
      "checkTime": 0, // 审核时间
      "checkRecord": "审核操作记录",
      "checkRemark": "审核备注"
    }
  ],
  // 确认检查项数据
  "check": [
    {
      "title": "确认项标题",
      "value": "确认结果",
      "checkTime": 0 // 确认时间
    }
  ]
}

```

#### 执行合约
请求执行合约，合约会根据请求参数和当前数据状态做相应的数据处理
执行合约具体包括的数据处理:
* 发起融资申请  
* 审核确认通过
* 已完成放款
* 已完成还款
* 流转申请到下一处理节点

```json
{
  "ledgerId": "账本ID",
  "orderKey": "订单key（账本存储对应的key）",
  "action": "具体操作"
}
```

#### 添加转账记录
接口记录放款时和还款时对应的转账记录
```json
{
  "ledgerId": "账本ID",
  "orderKey": "订单key（账本存储对应的key）",
  "transactionData": [
    {
      "expensesId": "支出方ID",
      "expenses": "支出方名称",
      "expensesAccount": {
       "account": "银行账户",
       "bankName": "银行名称",
       "bankBranch": "银行支行"
      },
      "expensesAmount": 0, // 支出金额
      "incomeId": "收入方ID",
      "income": "收入方名称",
      "incomeAccount":  {
        "account": "银行账户",
        "bankName": "银行名称",
        "bankBranch": "银行支行"
      },
      "remark": "转账备注",
      "images": ["转账凭据扫描图片Base64编码字符串"],
      "tansactionTime": 0, // 转账时间
      "incomConfirm": false // 收入方确认
    }
  ]
}
```

#### 转账记录确认
对已经添加的转账记录进行确认
```json
{
  "ledgerId": "账本ID",
  "orderKey": "订单key（账本存储对应的key）",
  "transactionData": ["转账流水编号"]
}
```

#### 融资申请列表查询
```json
{
  "ledgerId": "账本ID"
}
```

#### 融资申请详情查询
```json
{
  "ledgerId": "账本ID",
  "orderKey": "订单key（账本存储对应的key）"
}
```

#### 融资申请具体附件查看
```json
{
  "ledgerId": "账本ID",
  "orderKey": "订单key（账本存储对应的key）",
  "fileKey" : "文件的sha256值"
}
```





