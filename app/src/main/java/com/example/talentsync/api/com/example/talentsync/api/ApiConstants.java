package com.example.talentsync.api;

public class ApiConstants {
    // Change to your Flask server IP if needed
    public static final String BASE_URL = "http://192.168.43.224:5000";

    public static final String LOGIN_URL = BASE_URL + "/login";
    public static final String UPLOAD_RESUME_URL = BASE_URL + "/upload_resume";
    public static final String SYNC_INTERVIEW_URL = BASE_URL + "/api/sync_interview";
    public static final String JOB_MATCH_URL = BASE_URL + "/job_matches";
}
