package com.xiangan.platform.demo;

import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;
import org.hyperledger.fabric.sdk.ProposalResponse;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by liuzhudong on 2017/3/31.
 */
public class ProposalResponseResult {

    private Collection<ProposalResponse> successful = new LinkedList<>();
    private Collection<ProposalResponse> failed = new LinkedList<>();
    private TransactionEvent transactionEvent;
    private Throwable throwable;

    public Collection<ProposalResponse> getSuccessful() {
        return successful;
    }

    public void setSuccessful(Collection<ProposalResponse> successful) {
        this.successful.addAll(successful);
    }

    public Collection<ProposalResponse> getFailed() {
        return failed;
    }

    public void setFailed(Collection<ProposalResponse> failed) {
        this.failed.addAll(failed);
    }

    public void addSuccessful(ProposalResponse response) {
        this.successful.add(response);
    }

    public void addFailed(ProposalResponse response) {
        this.failed.add(response);
    }

    public boolean isSuccess() {
        return failed.size() <= 0;
    }

    public TransactionEvent getTransactionEvent() {
        return transactionEvent;
    }

    public void setTransactionEvent(TransactionEvent transactionEvent) {
        this.transactionEvent = transactionEvent;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
