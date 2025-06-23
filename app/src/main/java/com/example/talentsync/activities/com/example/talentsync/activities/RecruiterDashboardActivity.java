package com.example.talentsync.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.talentsync.R;

public class RecruiterDashboardActivity extends AppCompatActivity {

    private Button createJobBtn, viewCandidatesBtn, profileBtn, viewInterviewsBtn;
    private TextView nameText, emailText;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_dashboard);

        createJobBtn = findViewById(R.id.button_create_job);
        viewCandidatesBtn = findViewById(R.id.button_view_candidates);
        profileBtn = findViewById(R.id.button_profile);
        nameText = findViewById(R.id.text_name);
        emailText = findViewById(R.id.text_email);
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");

        nameText.setText("Welcome, " + name);
        emailText.setText("Email: " + email);
        Button viewInterviewsBtn = findViewById(R.id.button_view_scheduled_interviews);
        createJobBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateJobActivity.class));
        });

        viewInterviewsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RecruiterDashboardActivity.this, InterviewListActivity.class);
            startActivity(intent);
        });
        viewCandidatesBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, CandidateListActivity.class));
        });

        profileBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
    }
}