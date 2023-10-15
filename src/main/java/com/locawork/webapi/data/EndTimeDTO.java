package com.locawork.webapi.data;

public class EndTimeDTO {

    private String endTime;
    private int applyerId;
    private int jobId;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
