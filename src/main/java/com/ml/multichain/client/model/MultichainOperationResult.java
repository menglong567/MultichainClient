package com.ml.multichain.client.model;

/**
 * @author mengl
 */
public class MultichainOperationResult {
    private boolean result;
    private String msg;

    public MultichainOperationResult(String msg, boolean result) {
        this.msg = msg;
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
