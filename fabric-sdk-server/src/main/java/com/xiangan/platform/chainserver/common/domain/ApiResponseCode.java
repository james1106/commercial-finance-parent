package com.xiangan.platform.chainserver.common.domain;

/**
 * 接口响应状态定义
 *
 * @Creater liuzhudong
 * @Date 2017/4/13 09:21
 * @Version 1.0
 * @Copyright
 */
public class ApiResponseCode {
    private int code = 0;

    private String message = "OK";

    public ApiResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void message(String message) {
        this.setMessage(message);
    }
}
