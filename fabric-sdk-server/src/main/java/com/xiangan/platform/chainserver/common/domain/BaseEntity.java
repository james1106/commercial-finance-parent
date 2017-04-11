package com.xiangan.platform.chainserver.common.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

/**
 * 所有实体类的基类
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 11:17
 * @Version 1.0
 * @Copyright
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 6828627851398481806L;

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }
}
