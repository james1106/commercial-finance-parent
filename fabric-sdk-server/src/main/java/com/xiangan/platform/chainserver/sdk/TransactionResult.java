package com.xiangan.platform.chainserver.sdk;

/**
 * Transaction Result
 *
 * @Creater liuzhudong
 * @Date 2017/4/10 15:03
 * @Version 1.0
 * @Copyright
 */
public class TransactionResult {

    /**
     * 是否操作成功
     */
    private boolean success;

    /**
     * 异常
     */
    private Throwable throwable;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
