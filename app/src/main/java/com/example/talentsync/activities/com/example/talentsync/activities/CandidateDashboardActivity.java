package com.example.talentsync.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.example.talentsync.R;
import com.example.talentsync.utils.VolleyMultipartRequest;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;

public class CandidateDashboardActivity extends AppCompatActivity {

    private static final int PICK_RESUME_REQUEST = 1;
    private Uri resumeUri;
    private TextView resumeNameText;
    private Button selectResumeBtn, uploadResumeBtn;
    private ProgressDialog progressDialog;
    private TextView aqiText;
    private Object CandidateDashboardActivity;

    private TextView nameText, emailText;
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_resume);

        resumeNameText = findViewById(R.id.text_resume_name);
        selectResumeBtn = findViewById(R.id.button_select_resume);
        uploadResumeBtn = findViewById(R.id.button_upload_resume);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        aqiText = findViewById(R.id.text_aqi);
        nameText = findViewById(R.id.text_name);
        emailText = findViewById(R.id.text_email);

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");

        nameText.setText("Welcome, " + name);
        emailText.setText("Email: " + email);

        AqiRequestManager aqiManager = new AqiRequestManager(this);

        aqiManager.getAqiData("delhi", new AqiRequestManager.AqiResponseListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(JSONObject aqiData) {
                try {
                    JSONObject data = aqiData.getJSONObject("data");
                    int aqi = data.getInt("aqi");
                    String cityName = data.getJSONObject("city").getString("name");

                    TextView aqiText = findViewById(R.id.text_aqi);
                    aqiText.setText("AQI in " + cityName + ": " + aqi);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                Toast.makeText(CandidateDashboardActivity.this, "AQI Fetch Error: " + message, Toast.LENGTH_SHORT).show();
            }
        });

        selectResumeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, PICK_RESUME_REQUEST);
        });

        uploadResumeBtn.setOnClickListener(v -> {
            if (resumeUri != null) {
                uploadResume(resumeUri);
            } else {
                Toast.makeText(this, "Please select a resume first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_RESUME_REQUEST && resultCode == RESULT_OK && data != null) {
            resumeUri = data.getData();
            String name = getFileName(resumeUri);
            resumeNameText.setText(name);
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        }
        return result;
    }

    private void uploadResume(Uri fileUri) {
        progressDialog.show();
        try {
            @SuppressLint("Recycle") InputStream inputStream = getContentResolver().openInputStream(fileUri);
            assert inputStream != null;
            byte[] fileBytes = new byte[inputStream.available()];
            inputStream.read(fileBytes);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                    "http://192.168.43.224:5000/upload_resume",
                    response -> {
                        progressDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(new String(response.data));
                            Toast.makeText(this, "Uploaded! Matches: " + json.getJSONArray("matches").toString(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }) {

                @Override
                protected HashMap<String, DataPart> getByteData() {
                    HashMap<String, DataPart> params = new HashMap<>();
                    params.put("resume", new DataPart("resume.pdf", fileBytes));
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(volleyMultipartRequest);

        } catch (Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
            Toast.makeText(this, "File read error", Toast.LENGTH_SHORT).show();
        }
    }

    private static class AqiResponseListener {
    }

    private static class AqiRequestManager {
        public AqiRequestManager(Object candidateDashboardActivity) {

        }

        public void getAqiData(String delhi, AqiRequestManager.AqiResponseListener aqiResponseListener) {

        }

        public interface AqiResponseListener {
            void onSuccess(JSONObject aqiData);

            void onError(String message);
        }
    }
}