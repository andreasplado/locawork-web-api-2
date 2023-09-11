package com.locawork.webapi.data;

public class StartTimeDTO {

    private long startTime;
    private int applyerId;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getApplyerId() {
        return applyerId;
    }

    public void setApplyerId(int applyerId) {
        this.applyerId = applyerId;
    }
}
