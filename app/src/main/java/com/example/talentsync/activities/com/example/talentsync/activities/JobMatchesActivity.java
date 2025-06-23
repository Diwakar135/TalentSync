package com.example.talentsync.activities;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.talentsync.R;
import com.example.talentsync.adapter.JobAdapter;
import com.example.talentsync.models.Job;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JobMatchesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private JobAdapter adapter;
    private List<Job> jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_matches);

        recyclerView = findViewById(R.id.recycler_jobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jobList = new ArrayList<>();
        adapter = new JobAdapter(jobList);
        recyclerView.setAdapter(adapter);

        fetchMatchedJobs();
    }

    private void fetchMatchedJobs() {
        String url = "http://192.168.43.224:5000/job_matches";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    jobList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Job job = new Job(
                                    obj.getString("title"),
                                    obj.getString("company"),
                                    obj.getString("location"),
                                    obj.getString("skills")
                            );
                            jobList.add(job);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> {
                    Toast.makeText(this, "Failed to load jobs", Toast.LENGTH_SHORT).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
    }
}
