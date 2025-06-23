package com.example.talentsync.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.talentsync.R;
import com.example.talentsync.UploadResumeActivity;

public class CandidateListActivity extends AppCompatActivity {

    private Button uploadResumeBtn, viewMatchesBtn, editProfileBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_dashboard);

        uploadResumeBtn = findViewById(R.id.button_upload_resume);
        viewMatchesBtn = findViewById(R.id.button_view_matches);
        editProfileBtn = findViewById(R.id.button_edit_profile);

        uploadResumeBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, UploadResumeActivity.class));
        });

        viewMatchesBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, JobMatchesActivity.class));
        });

        editProfileBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
    }
}
