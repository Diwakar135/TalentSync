package com.example.talentsync.activities;

import android.annotation.SuppressLint;
import android.graphics.Insets;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.talentsync.R;

import org.json.JSONObject;
    public class CreateJobActivity extends AppCompatActivity {
        private static WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
            Insets systemBars = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()).toPlatformInsets();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            }
            return insets;
        }

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_job);
            int activityCreateJob = R.layout.activity_create_job;
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), CreateJobActivity::onApplyWindowInsets);
            EditText titleInput, descriptionInput, skillsInput;
            Button createJobBtn;
            titleInput = findViewById(R.id.edit_title);
            descriptionInput = findViewById(R.id.edit_description);
            skillsInput = findViewById(R.id.edit_skills);
            createJobBtn = findViewById(R.id.button_create);

            createJobBtn.setOnClickListener(v -> {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                String skills = skillsInput.getText().toString();

                JSONObject json = new JSONObject();
                try {
                    json.put("title", title);
                    json.put("description", description);
                    json.put("skills", skills);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String url = "http://192.168.43.224:5000/create_job";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json,
                        response -> Toast.makeText(this, "Job created", Toast.LENGTH_SHORT).show(),
                        error -> Toast.makeText(this, "Job creation failed", Toast.LENGTH_SHORT).show()
                );

                Volley.newRequestQueue(this).add(request);
            });
        }
    }
