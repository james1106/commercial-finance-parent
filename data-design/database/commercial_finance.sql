-- -- ----------------------------
-- -- 链外数据表脚本
-- -- 用户中心模块数据表设计
-- -- ----------------------------

-- -- 创建数据库
CREATE DATABASE IF NOT EXISTS `commercial_finance`;
USE `commercial_finance`;

-- -- 创建数据库表
-- -- 用户信息
CREATE TABLE IF NOT EXISTS `user_info` (
  `user_no`                         VARCHAR(36) NOT NULL COMMENT '用户编号: uuid',
  `login_account`                   VARCHAR(11) NOT NULL COMMENT '用户登录账号(手机)',
  `login_password`                  VARCHAR(36) NOT NULL COMMENT '用户登录密码',
  `name`                            VARCHAR(50) NOT NULL COMMENT '用户名称',
  `mobile`                          VARCHAR(11) NOT NULL COMMENT '用户手机号',
  `organization_no`                 VARCHAR(36) NOT NULL COMMENT '所属机构编号',
  `organization_name`               VARCHAR(50) NOT NULL COMMENT '所属机构名称',
  `organization_type`               VARCHAR(20) NOT NULL COMMENT '所属机构类型',
  `card_no`                         VARCHAR(20) NULL COMMENT '身份证',
  `status`                          TINYINT NOT NULL DEFAULT '0' COMMENT '状态:1=激活，2=冻结，3=注销',
  `email`                           VARCHAR(50) NULL COMMENT '邮箱',
  `roles`                           VARCHAR(200) NOT NULL COMMENT '角色(json数组)',
  `remark`                          VARCHAR(100) NULL COMMENT '备注',
  `creater`                         VARCHAR(50) NULL COMMENT '创建者',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `updater`                         VARCHAR(50) NULL COMMENT '更新者',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  `updater_ip`                      VARCHAR(20) NULL COMMENT '更新者IP',
  PRIMARY KEY (`login_account`),
  UNIQUE KEY `user_no_index` (`user_no`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '用户信息';

-- -- 机构信息
CREATE TABLE IF NOT EXISTS `organization_info` (
  `organization_no`                 VARCHAR(36) NOT NULL COMMENT '机构编号 uuid',
  `name`                            VARCHAR(50) NOT NULL COMMENT '机构名称',
  `type`                            VARCHAR(20) NOT NULL COMMENT '机构类型：供应商、核心企业、保理公司、银行',
  `corporate`                       VARCHAR(20) NOT NULL COMMENT '法人代表',
  -- 如果三证合一只需要填写统一社会信用代码
  `org_code`                        VARCHAR(36) NOT NULL COMMENT '机构代码或统一社会信用代码',
  `registed_no`                     VARCHAR(36) NOT NULL COMMENT '工商注册号',
  `tax_registration_certificate_no` VARCHAR(36) NOT NULL COMMENT '税务登记证编号',
  `introduction`                    VARCHAR(100) NULL COMMENT '机构简介',
  `register_address`                VARCHAR(50) NULL COMMENT '注册地址',
  `address`                         VARCHAR(50) NULL COMMENT '机构地址',
  `telephone`                       VARCHAR(20) NULL COMMENT '联系电话',
  `email`                           VARCHAR(50) NULL COMMENT '邮件',
  `status`                          TINYINT NOT NULL DEFAULT '0' COMMENT '状态：1=激活，2=冻结，3=注销',
  `contact`                         VARCHAR(200) NULL COMMENT '联系人(json格式数据)',
  `category`                        VARCHAR(45) NULL COMMENT '机构所属行业分类',
  `creater`                         VARCHAR(50) NULL COMMENT '创建者',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `updater`                         VARCHAR(50) NULL COMMENT '更新者',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  `updater_ip`                      VARCHAR(20) NULL COMMENT '更新者IP',
  PRIMARY KEY (`organization_no`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '机构信息';

-- -- 机构文件
CREATE TABLE IF NOT EXISTS `organization_file` (
  `id`                              INT NOT NULL auto_increment COMMENT '主键ID',
  `organization_no`                 VARCHAR(36) NOT NULL COMMENT '机构编号 uuid',
  `data`                            BINARY NOT NULL COMMENT '文件protobuf数据:二进制',
  PRIMARY KEY (`id`),
  KEY `organization_no_index` (`organization_no`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '机构文件';

-- -- 发票数据管理
CREATE TABLE IF NOT EXISTS `invoice` (
  `invoice_no`                      VARCHAR(36) NOT NULL COMMENT '发票号',
  `order_no`                        VARCHAR(36) NULL COMMENT '融资申请编号',
  `invoice_amount`                  BIGINT NOT NULL DEFAULT '0' COMMENT '应收账款发票金额',
  `invoice_time`                    DATE NOT NULL COMMENT '开票日期',
  `supplier_no`                     VARCHAR(36) NOT NULL COMMENT '销售方编号',
  `supplier_name`                   VARCHAR(50) NOT NULL COMMENT '销售方名称',
  `enterprise_no`                   VARCHAR(36) NOT NULL COMMENT '购买方编号',
  `enterprise_name`                 VARCHAR(50) NOT NULL COMMENT '购买方名称',
  `contract_no`                     VARCHAR(50) NULL COMMENT '购销合同编号',
  `status`                          TINYINT NOT NULL DEFAULT '0' COMMENT '发票状态:1=未使用,2=使用中,3=已使用',
  `remark`                          VARCHAR(200) NULL COMMENT '备注',
  `creater`                         VARCHAR(50) NULL COMMENT '创建者',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `updater`                         VARCHAR(50) NULL COMMENT '更新者',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  `updater_ip`                      VARCHAR(20) NULL COMMENT '更新者IP',
  PRIMARY KEY (`invoice_no`),
  KEY `supplier_no_key` (`supplier_no`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '发票数据管理';

-- -- 发票文件
CREATE TABLE IF NOT EXISTS `invoice_file` (
  `id`                              INT NOT NULL auto_increment COMMENT '主键ID',
  `invoice_no`                      VARCHAR(36) NOT NULL COMMENT '发票编号',
  `data`                            BINARY NOT NULL COMMENT '文件protobuf数据',
  PRIMARY KEY (`id`),
  KEY `invoice_no_index` (`invoice_no`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '发票文件';


-- -- 机构关系信息表(账本信息)
CREATE TABLE IF NOT EXISTS `organization_relation` (
  `ledger_id`                       VARCHAR(50) NOT NULL COMMENT '账本ID',
  `supplier_no`                     VARCHAR(36) NULL COMMENT '供应商编号',
  `supplier_name`                   VARCHAR(50) NULL COMMENT '供应商名称',
  `enterprise_no`                   VARCHAR(36) NULL COMMENT '核心企业编号',
  `enterprise_name`                 VARCHAR(50) NULL COMMENT '核心企业名称',
  `bankfactor_no`                   VARCHAR(36) NULL COMMENT '保理公司编号',
  `bankfactor_name`                 VARCHAR(50) NULL COMMENT '保理公司名称',
  `bank_no`                         VARCHAR(36) NULL COMMENT '银行编号',
  `bank_name`                       VARCHAR(50) NULL COMMENT '银行名称',
  `relation_banks`                  VARCHAR(200) NULL COMMENT '三方账本关联银行(json格式数据)',
  `type`                            TINYINT NOT NULL DEFAULT '0' COMMENT '关系类型:1=三方账本,2=银行保理双方账本',
  `default_rate`                    DECIMAL(10,4) NOT NULL DEFAULT '0' COMMENT '默认融资利率',
  `creater`                         VARCHAR(50) NULL COMMENT '创建者',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `updater`                         VARCHAR(50) NULL COMMENT '更新者',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  `updater_ip`                      VARCHAR(20) NULL COMMENT '更新者IP',
  PRIMARY KEY (`ledger_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '机构关系信息';

-- -- 银行加入三方账本数据管理
-- -- 主要实现从银行维度查询账本
CREATE TABLE IF NOT EXISTS `bank_organization_relation` (
  `ledger_id`                       VARCHAR(50) NOT NULL COMMENT '三方账本ID',
  `bank_no`                         VARCHAR(36) NULL COMMENT '银行编号',
  `bank_name`                       VARCHAR(50) NULL COMMENT '银行名称',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  PRIMARY KEY (`bank_no`,`ledger_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '银行和三方账本关系信息';


-- -- 三方融资申请
CREATE TABLE IF NOT EXISTS `financing_order` (
  `id`                              INT NOT NULL auto_increment COMMENT '主键ID',
  `order_no`                        VARCHAR(36) NOT NULL COMMENT '订单编号',
  `ledger_id`                       VARCHAR(50) NOT NULL COMMENT '账本的channel名称',
  `supplier_no`                     VARCHAR(36) NOT NULL COMMENT '供应商编号',
  `supplier_name`                   VARCHAR(50) NOT NULL COMMENT '供应商名称',
  `enterprise_no`                   VARCHAR(36) NOT NULL COMMENT '核心企业编号',
  `enterprise_name`                 VARCHAR(50) NOT NULL COMMENT '核心企业名称',
  `bankfactor_no`                   VARCHAR(36) NOT NULL COMMENT '保理公司编号',
  `bankfactor_name`                 VARCHAR(50) NOT NULL COMMENT '保理公司名称',
  -- 订单状态待确定
  `status`                          TINYINT NOT NULL DEFAULT '0' COMMENT '订单状态',
  `confirmed_amount`                DECIMAL(10,4) NOT NULL DEFAULT '0' COMMENT '融资金额',
  `confirmed_rate`                  DECIMAL(10,4) NOT NULL DEFAULT '0' COMMENT '融资利率',
  `confirmed_expired_time`          DATETIME NOT NULL COMMENT '融资过期时间',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_key` (`order_no`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '保理融资申请管理';

-- -- -----------------------------------------
-- -- 角色管理
CREATE TABLE IF NOT EXISTS `role` (
  `organization_no`                 VARCHAR(36) NOT NULL COMMENT '机构编号',
  `role_code`                       VARCHAR(36) NOT NULL COMMENT '角色编码',
  `role_name`                       VARCHAR(50) NOT NULL COMMENT '角色名称',
  `role_desc`                       VARCHAR(100) NOT NULL COMMENT '角色描述',
  `creater`                         VARCHAR(50) NULL COMMENT '创建者',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `updater`                         VARCHAR(50) NULL COMMENT '更新者',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  `updater_ip`                      VARCHAR(20) NULL COMMENT '更新者IP',
  PRIMARY KEY (`organization_no`,`role_code`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '角色信息';

-- -- API资源管理
CREATE TABLE IF NOT EXISTS `system_source` (
  `source_code`                     VARCHAR(36) NOT NULL COMMENT '资源编码',
  `parent_code`                     VARCHAR(36) NULL COMMENT '所属资源编码',
  `source_type`                     TINYINT NOT NULL DEFAULT '0' COMMENT '资源类型:1=模块,2=菜单,3=API,4=按钮',
  `source_name`                     VARCHAR(50) NOT NULL COMMENT '资源名称',
  `source_path`                     VARCHAR(100) NOT NULL COMMENT '资源路径',
  `creater`                         VARCHAR(50) NULL COMMENT '创建者',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `updater`                         VARCHAR(50) NULL COMMENT '更新者',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  `updater_ip`                      VARCHAR(20) NULL COMMENT '更新者IP',
  PRIMARY KEY (`source_code`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '角色信息';


-- -- 角色资源管理
CREATE TABLE IF NOT EXISTS `role_source` (
  `organization_no`                 VARCHAR(36) NOT NULL COMMENT '机构编号',
  `role_code`                       VARCHAR(36) NOT NULL COMMENT '角色编码',
  `source_code`                     VARCHAR(36) NOT NULL COMMENT '资源编码',
  `creater`                         VARCHAR(50) NULL COMMENT '创建者',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `updater`                         VARCHAR(50) NULL COMMENT '更新者',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  `updater_ip`                      VARCHAR(20) NULL COMMENT '更新者IP',
  PRIMARY KEY (`organization_no`,`role_code` ,`source_code` )
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '角色资源管理';

-- -- 融资申请订单阶段管理
CREATE TABLE IF NOT EXISTS `financing_order_step_node` (
  `ledger_id`                       VARCHAR(36) NOT NULL COMMENT '账本ID',
  `step_id`                         VARCHAR(36) NOT NULL COMMENT '订单阶段ID',
  `step_name`                       VARCHAR(36) NOT NULL COMMENT '订单阶段名称',
  `organization_no`                 VARCHAR(36) NOT NULL COMMENT '机构编号',
  `organization_name`               VARCHAR(50) NOT NULL COMMENT '机构名称',
  `remark`                          VARCHAR(100) NULL COMMENT '节点备注',
  `is_start_node`                   TINYINT NOT NULL DEFAULT '0' COMMENT '是否是开始节点',
  `is_end_node`                     TINYINT NOT NULL DEFAULT '0' COMMENT '是否是结束节点',
  `next_step_id`                    VARCHAR(36) NULL COMMENT '订单的下一阶段ID',
  `creater`                         VARCHAR(50) NULL COMMENT '创建者',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `updater`                         VARCHAR(50) NULL COMMENT '更新者',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  `updater_ip`                      VARCHAR(20) NULL COMMENT '更新者IP',
  PRIMARY KEY (`ledger_id`,`step_id` )
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '融资申请订单阶段管理';

-- -- 订单在此阶段需要通过的流程审批
CREATE TABLE IF NOT EXISTS `step_node_check_flows` (
  `id`                              INT NOT NULL auto_increment COMMENT '主键ID',
  `ledger_id`                       VARCHAR(36) NOT NULL COMMENT '账本ID',
  `step_id`                         VARCHAR(36) NOT NULL COMMENT '订单阶段ID',
  `template_id`                     VARCHAR(36) NOT NULL COMMENT '审批流程模板ID',
  `order_no`                        VARCHAR(36) NOT NULL COMMENT '订单编号',
  `execute_order`                   INT DEFAULT '1' COMMENT '执行顺序',
  `creater`                         VARCHAR(50) NULL COMMENT '创建者',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `updater`                         VARCHAR(50) NULL COMMENT '更新者',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  `updater_ip`                      VARCHAR(20) NULL COMMENT '更新者IP',
  PRIMARY KEY (`id` ),
  KEY `order_index` (`ledger_id`,`step_id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '订单在此阶段需要通过的流程审批';

-- -- 审批流程模板
CREATE TABLE IF NOT EXISTS `check_flow_template` (
  `template_id`                     VARCHAR(36) NOT NULL COMMENT '审批流程模板ID',
  `organization_no`                 VARCHAR(36) NOT NULL COMMENT '机构编号',
  `organization_name`               VARCHAR(50) NOT NULL COMMENT '机构名称',
  `title`                           VARCHAR(50) NOT NULL COMMENT '模板标题',
  `system_type`                     TINYINT NOT NULL DEFAULT '0' COMMENT '流程类型:0=自身系统流程,1=其他系统提供流程实现',
  `provider`                        VARCHAR(200) NULL COMMENT '其他系统流程实现提供配置',
  `creater`                         VARCHAR(50) NULL COMMENT '创建者',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `updater`                         VARCHAR(50) NULL COMMENT '更新者',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  `updater_ip`                      VARCHAR(20) NULL COMMENT '更新者IP',
  PRIMARY KEY (`template_id` ),
  KEY `organization_no_index` (`organization_no`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '审批流程模板节点数据';

-- -- 审批流程模板节点数据
-- -- 流程模板固化两个节点:开始节点、结束节点
CREATE TABLE IF NOT EXISTS `check_flow_node` (
  `template_id`                     VARCHAR(36) NOT NULL COMMENT '审批流程模板ID',
  `node_id`                         VARCHAR(36) NOT NULL COMMENT '节点ID',
  `node_name`                       VARCHAR(36) NOT NULL COMMENT '节点名称',
  `remark`                          VARCHAR(100) NULL COMMENT '节点备注',
  `previous_node_id`                VARCHAR(36) NULL COMMENT '上一节点ID',
  `next_node_id`                    VARCHAR(36) NULL COMMENT '下一节点ID',
  `is_start_node`                   TINYINT NOT NULL DEFAULT '0' COMMENT '是否是开始节点',
  `is_end_node`                     TINYINT NOT NULL DEFAULT '0' COMMENT '是否是结束节点',
  `creater`                         VARCHAR(50) NULL COMMENT '创建者',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `updater`                         VARCHAR(50) NULL COMMENT '更新者',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  `updater_ip`                      VARCHAR(20) NULL COMMENT '更新者IP',
  PRIMARY KEY (`template_id`,`node_id` )
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '审批流程模板节点数据';

-- -- 审批流程模板节点操作用户管理
CREATE TABLE IF NOT EXISTS `check_flow_node_user` (
  `id`                              INT NOT NULL auto_increment COMMENT '主键ID',
  `template_id`                     VARCHAR(36) NOT NULL COMMENT '审批流程模板ID',
  `node_id`                         VARCHAR(36) NOT NULL COMMENT '节点ID',
  `organization_no`                 VARCHAR(36) NOT NULL COMMENT '机构编号',
  `role_code`                       VARCHAR(36) NULL COMMENT '角色编码',
  `user_no`                         VARCHAR(36) NULL COMMENT '用户编码',
  `creater`                         VARCHAR(50) NULL COMMENT '创建者',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `updater`                         VARCHAR(50) NULL COMMENT '更新者',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  `updater_ip`                      VARCHAR(20) NULL COMMENT '更新者IP',
  PRIMARY KEY (`id` ),
  KEY `template_node_id_index` (`template_id`,`node_id` )
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '审批流程模板节点操作用户管理';

-- -- 申请订单审批流程历史
CREATE TABLE IF NOT EXISTS `order_check_flow_history` (
  `id`                              INT NOT NULL auto_increment COMMENT '主键ID',
  `order_no`                        VARCHAR(36) NOT NULL COMMENT '订单编号',
  `ledger_id`                       VARCHAR(36) NOT NULL COMMENT '账本ID',
  `order_step`                      TINYINT NOT NULL DEFAULT '0' COMMENT '订单阶段',
  `template_id`                     VARCHAR(36) NOT NULL COMMENT '审批流程模板ID',
  `organization_no`                 VARCHAR(36) NOT NULL COMMENT '机构编号',
  `organization_name`               VARCHAR(50) NOT NULL COMMENT '机构名称',
  `current_node_id`                 VARCHAR(36) NOT NULL COMMENT '当前节点ID',
  `previous_node_id`                VARCHAR(36) NOT NULL COMMENT '上一节点ID',
  `next_node_id`                    VARCHAR(36) NOT NULL COMMENT '下一节点ID',
  `operate_user_no`                 VARCHAR(36) NOT NULL COMMENT '操作用户编号',
  `operate_user_name`               VARCHAR(50) NOT NULL COMMENT '操作用户名称',
  `creater_ip`                      VARCHAR(20) NULL COMMENT '创建者IP',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  PRIMARY KEY (`id` ),
  KEY `order_index` (`order_no`,`order_step`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '申请订单审批流程历史';


-- -- ----------------------------------------
-- -- 账本缓存数据
-- -- ----------------------------------------
-- -- 三方融资申请
CREATE TABLE IF NOT EXISTS `financing_order` (
  `id`                              INT NOT NULL auto_increment COMMENT '主键ID',
  `order_no`                        VARCHAR(36) NOT NULL COMMENT '订单编号',
  `ledger_id`                       VARCHAR(50) NOT NULL COMMENT '账本ID',
  `supplier_no`                     VARCHAR(36) NOT NULL COMMENT '供应商编号',
  `supplier_name`                   VARCHAR(50) NOT NULL COMMENT '供应商名称',
  `enterprise_no`                   VARCHAR(36) NOT NULL COMMENT '核心企业编号',
  `enterprise_name`                 VARCHAR(50) NOT NULL COMMENT '核心企业名称',
  `bankfactor_no`                   VARCHAR(36) NOT NULL COMMENT '保理公司编号',
  `bankfactor_name`                 VARCHAR(50) NOT NULL COMMENT '保理公司名称',
  `step`                            TINYINT NOT NULL DEFAULT '0' COMMENT '订单阶段',
  `check_flow_node_name`            VARCHAR(50) NULL COMMENT '审批节点名称',
  `confirmed_amount`                BIGINT NOT NULL DEFAULT '0' COMMENT '融资金额',
  `confirmed_rate`                  BIGINT NOT NULL DEFAULT '0' COMMENT '融资利率',
  `confirmed_expired_time`          DATETIME NOT NULL COMMENT '融资过期时间',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_key` (`order_no`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '保理融资申请管理';

-- -- 保理向银行贷款申请
CREATE TABLE IF NOT EXISTS `bank_loan_order` (
  `id`                              INT NOT NULL auto_increment COMMENT '主键ID',
  `order_no`                        VARCHAR(36) NOT NULL COMMENT '订单编号',
  `ledger_id`                       VARCHAR(50) NOT NULL COMMENT '账本ID',
  `bankfactor_no`                   VARCHAR(36) NOT NULL COMMENT '保理公司编号',
  `bankfactor_name`                 VARCHAR(50) NOT NULL COMMENT '保理公司名称',
  `bank_no`                         VARCHAR(36) NOT NULL COMMENT '银行编号',
  `bank_name`                       VARCHAR(50) NOT NULL COMMENT '银行名称',
  `step`                            TINYINT NOT NULL DEFAULT '0' COMMENT '订单阶段',
  `check_flow_node_name`            VARCHAR(50) NULL COMMENT '审批节点名称',
  `confirmed_amount`                BIGINT NOT NULL DEFAULT '0' COMMENT '融资金额',
  `confirmed_rate`                  BIGINT NOT NULL DEFAULT '0' COMMENT '融资利率',
  `confirmed_expired_time`          DATETIME NOT NULL COMMENT '融资过期时间',
  `create_time`                     DATETIME NULL COMMENT '创建时间',
  `update_time`                     DATETIME NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_key` (`order_no`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COMMENT = '银行贷款申请管理';

