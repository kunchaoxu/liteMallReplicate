package com.liteMallReplicate.litemallcore.notify;

public class SmsResult {
    private boolean successful;
    private Object result;

    public boolean isSuccessful() { return successful};

    public void setSuccessful(boolean successful) { this.successful = successful; }

    public Object getResult() { return result; }

    public void setResult(Object result) { this.result = result; }
}
