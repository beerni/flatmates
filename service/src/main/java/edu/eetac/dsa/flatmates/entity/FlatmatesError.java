package edu.eetac.dsa.flatmates.entity;

/**
 * Created by bernat on 9/12/15.
 */
public class FlatmatesError {
    private int status;
    private String reason;

    public FlatmatesError() {
    }

    public FlatmatesError(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
