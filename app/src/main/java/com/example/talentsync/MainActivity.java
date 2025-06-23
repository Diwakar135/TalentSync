package com.example.talentsync;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.talentsync.R;
import com.example.talentsync.activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private Button recruiterLoginBtn, candidateLoginBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recruiterLoginBtn = findViewById(R.id.button_recruiter);
        candidateLoginBtn = findViewById(R.id.button_candidate);

        recruiterLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("userType", "recruiter");
                startActivity(intent);
            }
        });

        candidateLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("userType", "candidate");
                startActivity(intent);
            }
        });
    }
}