package com.xiangan.platform.chainserver.sdk.exception;

/**
 * 多个peer节点操作返回的结果不一致异常
 *
 * @Creater liuzhudong
 * @Date 2017/4/14 16:14
 * @Version 1.0
 * @Copyright
 */
public class PeersResponseNotSameException extends Exception {
    private static final long serialVersionUID = 8207882881196140925L;

    public PeersResponseNotSameException() {
        super();
    }

    public PeersResponseNotSameException(String message, Throwable parent) {
        super(message, parent);
    }

    public PeersResponseNotSameException(String message) {
        super(message);
    }

    public PeersResponseNotSameException(Throwable t) {
        super(t);
    }
}