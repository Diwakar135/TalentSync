package com.example.talentsync.models;

import java.io.Serializable;

public class Candidate implements Serializable {
    private String name;
    private String email;
    private String skills;
    private String experience;

    public Candidate(String name, String email, String skills, String experience) {
        this.name = name;
        this.email = email;
        this.skills = skills;
        this.experience = experience;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSkills() {
        return skills;
    }

    public String getExperience() {
        return experience;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
