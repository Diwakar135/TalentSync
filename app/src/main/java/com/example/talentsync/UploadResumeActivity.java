package com.example.talentsync;

import static com.android.volley.Request.Method.POST;

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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.example.talentsync.utils.VolleyMultipartRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

public class UploadResumeActivity extends AppCompatActivity {

    private static final int PICK_PDF_REQUEST = 1;
    private Uri resumeUri;
    private TextView fileNameText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_resume);

        fileNameText = findViewById(R.id.text_filename);
        Button selectBtn = findViewById(R.id.button_select);
        Button uploadBtn = findViewById(R.id.button_upload);

        selectBtn.setOnClickListener(v -> pickResume());
        uploadBtn.setOnClickListener(v -> uploadResume());
    }

    private void pickResume() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select Resume"), PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null) {
            resumeUri = data.getData();
            fileNameText.setText(getFileName(resumeUri));
        }
    }

    private void uploadResume() {
        if (resumeUri == null) {
            Toast.makeText(this, "Select a resume first", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        try {
            InputStream inputStream = getContentResolver().openInputStream(resumeUri);
            assert inputStream != null;
            byte[] resumeBytes = new byte[inputStream.available()];
            inputStream.read(resumeBytes);
            inputStream.close();

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(POST,
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
                protected Map<String, DataPart> getByteData() throws AuthFailureError {
                    return Collections.emptyMap();
                }
            };

            Request<NetworkResponse> add = Volley.newRequestQueue(this).add(volleyMultipartRequest);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileName(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        assert cursor != null;
        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        String name = cursor.getString(nameIndex);
        cursor.close();
        return name;
    }
}
