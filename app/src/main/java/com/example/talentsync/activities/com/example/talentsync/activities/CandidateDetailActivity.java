package com.example.talentsync.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.talentsync.R;
import com.example.talentsync.models.Candidate;

public class CandidateDetailActivity extends AppCompatActivity {

    private TextView nameTextView, emailTextView, skillsTextView, experienceTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_detail);

        nameTextView = findViewById(R.id.text_candidate_name);
        emailTextView = findViewById(R.id.text_candidate_email);
        skillsTextView = findViewById(R.id.text_candidate_skills);
        experienceTextView = findViewById(R.id.text_candidate_experience);

        Candidate candidate = (Candidate) getIntent().getSerializableExtra("candidate");

        if (candidate != null) {
            nameTextView.setText(candidate.getName());
            emailTextView.setText(candidate.getEmail());
            skillsTextView.setText(candidate.getSkills());
            experienceTextView.setText(candidate.getExperience());
        }
    }
}