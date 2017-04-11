package com.xiangan.platform.chainserver.sdk.exception;

/**
 * chaincode invoke exception
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 13:49
 * @Version 1.0
 * @Copyright
 */
public class InvokeChainCodeException extends Exception {
    private static final long serialVersionUID = 8207882881196140925L;

    public InvokeChainCodeException(String message, Throwable parent) {
        super(message, parent);
    }

    public InvokeChainCodeException(String message) {
        super(message);
    }

    public InvokeChainCodeException(Throwable t) {
        super(t);
    }
}
