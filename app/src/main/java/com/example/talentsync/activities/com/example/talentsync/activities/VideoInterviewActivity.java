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

public class VideoInterviewActivity extends AppCompatActivity {

    private TextView interviewTitleText, videoInterviewInstructionsText;
    private Button startVideoCallBtn;

    private String videoLink = "https://meet.example.com/video123"; // Replace with actual dynamic link

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_interview);

        interviewTitleText = findViewById(R.id.text_video_interview_title);
        videoInterviewInstructionsText = findViewById(R.id.text_video_instructions);
        startVideoCallBtn = findViewById(R.id.button_start_video_call);

        // Dummy content (replace with intent extras or fetched data)
        interviewTitleText.setText("Live Video Interview - Software Engineer");
        videoInterviewInstructionsText.setText("Click the button below to join your scheduled video interview on time. Make sure your mic and camera are active.");

        startVideoCallBtn.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Unable to open video link", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
