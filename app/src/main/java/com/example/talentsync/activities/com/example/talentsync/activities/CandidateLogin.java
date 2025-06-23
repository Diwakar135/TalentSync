package com.example.talentsync.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.talentsync.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CandidateLogin extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_login);

        emailInput = findViewById(R.id.edit_email);
        passwordInput = findViewById(R.id.edit_password);
        loginBtn = findViewById(R.id.button_login);

        loginBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                loginCandidate(email, password);
            }
        });
    }

    private void loginCandidate(String email, String password) {
        String url = "http://192.168.43.224:5000/auth/candidate_login"; // Use your server IP

        JSONObject data = new JSONObject();
        try {
            data.put("email", email);
            data.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                data,
                response -> {
                    try {
                        if (response.getBoolean("success")) {
                            String name = response.getString("name");
                            Toast.makeText(this, "Welcome, " + name, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(this, CandidateDashboardActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("email", response.getString("email"));
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Login failed: " + response.getString("error"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Response error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
    }
}
