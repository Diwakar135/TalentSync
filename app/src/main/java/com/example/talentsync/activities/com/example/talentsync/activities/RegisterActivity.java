package com.example.talentsync.activities;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.talentsync.R;

import org.json.*;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput;
    private Spinner roleSpinner;
    private Button registerBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = findViewById(R.id.edit_name);
        emailInput = findViewById(R.id.edit_email);
        passwordInput = findViewById(R.id.edit_password);
        roleSpinner = findViewById(R.id.spinner_role);
        registerBtn = findViewById(R.id.button_register);
        Button loginBtn = findViewById(R.id.button_to_login);
        loginBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        registerBtn.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String role = roleSpinner.getSelectedItem().toString().toLowerCase();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject data = new JSONObject();
        try {
            data.put("name", name);
            data.put("email", email);
            data.put("password", password);
            data.put("role", role);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        KeyguardManager.KeyguardLockedStateListener candidate = response -> {
            Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();

            if (role.equals("candidate")) {
                startActivity(new Intent(this, CandidateDashboardActivity.class));
            } else {
                startActivity(new Intent(this, RecruiterDashboardActivity.class));
            }

            finish();
        };


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                "http://192.168.43.224:5000/auth/register", data,
                response -> {
                    Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                },
                error -> Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(request);
    }
}
