package com.xiangan.platform.chainserver.common.domain;

/**
 * 响应结果基类
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 12:09
 * @Version 1.0
 * @Copyright
 */
public class BaseResult extends BaseEntity {
    private static final long serialVersionUID = -7051978822759050280L;

    /**
     * 响应状态编码
     */
    private int code = 0;

    /**
     * 响应状态消息描述
     */
    private String message = "OK";

    /**
     * 返回结果
     * <p>
     * 加密之后的字符串
     * </p>
     */
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BaseResult(){

    }

    public BaseResult(ApiResponseCode code){
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
