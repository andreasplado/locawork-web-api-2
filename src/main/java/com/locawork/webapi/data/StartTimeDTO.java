package com.locawork.webapi.data;

public class StartTimeDTO {

    private String startTime;
    private int applyerId;
    private int jobId;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getApplyerId() {
        return applyerId;
    }

    public void setApplyerId(int applyerId) {
        this.applyerId = applyerId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
