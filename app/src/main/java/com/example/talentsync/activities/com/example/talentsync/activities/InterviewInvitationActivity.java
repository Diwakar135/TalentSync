package com.example.talentsync.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.talentsync.R;

public class InterviewInvitationActivity extends AppCompatActivity {

    private TextView interviewTitle, interviewTime, interviewLink;
    private Button joinInterviewBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_invitation);

        interviewTitle = findViewById(R.id.text_interview_title);
        interviewTime = findViewById(R.id.text_interview_time);
        interviewLink = findViewById(R.id.text_interview_link);
        joinInterviewBtn = findViewById(R.id.button_join_interview);

        // Example dummy data (replace with real intent extras or API data)
        String title = "Android Developer Interview";
        String time = "2025-06-21 10:30 AM";
        String link = "https://meet.example.com/interview123";

        interviewTitle.setText(title);
        interviewTime.setText(time);
        interviewLink.setText(link);

        joinInterviewBtn.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Invalid link", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
