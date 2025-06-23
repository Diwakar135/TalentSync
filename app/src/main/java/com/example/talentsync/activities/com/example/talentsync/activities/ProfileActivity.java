package com.example.talentsync.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.talentsync.R;

public class ProfileActivity extends AppCompatActivity {

    private EditText editName, editEmail, editSkills, editExperience;
    private Button saveProfileBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editSkills = findViewById(R.id.edit_skills);
        editExperience = findViewById(R.id.edit_experience);
        saveProfileBtn = findViewById(R.id.button_save_profile);

        // You can pre-fill user profile data here using shared prefs or an API

        saveProfileBtn.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String skills = editSkills.getText().toString().trim();
            String experience = editExperience.getText().toString().trim();

            // Validate input
            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Name and Email are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: Send data to server or save locally
            Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show();
        });
    }
}
