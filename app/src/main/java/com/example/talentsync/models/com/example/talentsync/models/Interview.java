package com.example.talentsync.models;

public class Interview {
    private String id;
    private String candidateId;
    private String jobId;
    private String scheduledDateTime;
    private String status;

    public Interview(String id, String candidateId, String jobId, String scheduledDateTime, String status) {
        this.id = id;
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.scheduledDateTime = scheduledDateTime;
        this.status = status;
    }

    public Interview(int id, String title, String datetime) {

    }

    public String getId() {
        return id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public String getJobId() {
        return jobId;
    }

    public String getScheduledDateTime() {
        return scheduledDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
