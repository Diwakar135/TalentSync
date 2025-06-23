package com.example.talentsync.models;

import java.util.List;

public class ResumeAnalysis {
    private String candidateId;
    private List<String> extractedSkills;
    private List<String> matchedJobs;
    private float matchScore;

    public ResumeAnalysis(String candidateId, List<String> extractedSkills, List<String> matchedJobs, float matchScore) {
        this.candidateId = candidateId;
        this.extractedSkills = extractedSkills;
        this.matchedJobs = matchedJobs;
        this.matchScore = matchScore;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public List<String> getExtractedSkills() {
        return extractedSkills;
    }

    public List<String> getMatchedJobs() {
        return matchedJobs;
    }

    public float getMatchScore() {
        return matchScore;
    }
}
