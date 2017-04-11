package com.xiangan.platform.chainserver.sdk.exception;

/**
 * chaincode query exception
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 13:49
 * @Version 1.0
 * @Copyright
 */
public class QueryChainCodeException extends Exception {
    private static final long serialVersionUID = 8207882881196140925L;

    public QueryChainCodeException(String message, Throwable parent) {
        super(message, parent);
    }

    public QueryChainCodeException(String message) {
        super(message);
    }

    public QueryChainCodeException(Throwable t) {
        super(t);
    }
}
