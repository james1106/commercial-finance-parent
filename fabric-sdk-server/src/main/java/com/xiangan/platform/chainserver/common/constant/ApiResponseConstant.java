package com.xiangan.platform.chainserver.common.constant;

import com.xiangan.platform.chainserver.common.domain.ApiResponseCode;

/**
 * 接口响应状态码定义
 *
 * @Creater liuzhudong
 * @Date 2017/4/13 09:24
 * @Version 1.0
 * @Copyright
 */
public class ApiResponseConstant {

    public static ApiResponseCode SUCCESS = new ApiResponseCode(0, "OK");

    public static ApiResponseCode SERVER_EEROR = new ApiResponseCode(999, "server error");

    public static ApiResponseCode PARAM_EEROR = new ApiResponseCode(1000, "request param error");

}
