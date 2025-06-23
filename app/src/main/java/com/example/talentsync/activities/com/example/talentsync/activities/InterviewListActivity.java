package com.example.talentsync.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talentsync.R;
import com.example.talentsync.InterviewDbHelper;
import com.example.talentsync.models.Interview;
import com.example.talentsync.adapter.InterviewAdapter;

import java.util.ArrayList;
import java.util.List;

public class InterviewListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InterviewAdapter adapter;
    private List<Interview> interviewList = new ArrayList<>();
    private InterviewDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_list);

        recyclerView = findViewById(R.id.recycler_interviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new InterviewAdapter(interviewList);
        recyclerView.setAdapter(adapter);

        loadInterviews();
    }

    private void loadInterviews() {
        Cursor cursor = dbHelper.getAllInterviews();
        interviewList.clear();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String datetime = cursor.getString(cursor.getColumnIndexOrThrow("datetime"));

                interviewList.add(new Interview(id, title, datetime));
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "No interviews scheduled", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }
}
