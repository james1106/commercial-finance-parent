package com.xiangan.platform.chainserver.sdk.exception;

/**
 * chaincode instantiation exception
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 13:49
 * @Version 1.0
 * @Copyright
 */
public class SendInstantiationProposalException extends Exception {
    private static final long serialVersionUID = 8207882881196140925L;

    public SendInstantiationProposalException(String message, Throwable parent) {
        super(message, parent);
    }

    public SendInstantiationProposalException(String message) {
        super(message);
    }

    public SendInstantiationProposalException(Throwable t) {
        super(t);
    }
}
