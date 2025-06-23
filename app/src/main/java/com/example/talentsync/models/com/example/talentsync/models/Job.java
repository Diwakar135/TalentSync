package com.example.talentsync.models;

public class Job {
    private String id;
    private String title;
    private String description;
    private String company;
    private String location;
    private String recruiterId;

    public Job(String id, String title, String description, String company) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.company = company;
        this.location = location;
        this.recruiterId = recruiterId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getRecruiterId() {
        return recruiterId;
    }
}
