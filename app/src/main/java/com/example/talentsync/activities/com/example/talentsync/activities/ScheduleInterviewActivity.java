package com.example.talentsync.activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.talentsync.InterviewDbHelper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.talentsync.R;
import com.example.talentsync.receiver.InterviewAlarmReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScheduleInterviewActivity extends AppCompatActivity {

    public Object scheduleInterviewActivity;
    private TextView selectedDateTime;
    private EditText editInterviewTitle;
    private Button pickDateTimeBtn, scheduleBtn;
    private String scheduledDateTime = "";

    @SuppressLint({"MissingInflatedId", "ScheduleExactAlarm"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_interview);

        selectedDateTime = findViewById(R.id.text_selected_datetime);
        editInterviewTitle = findViewById(R.id.edit_interview_title);
        pickDateTimeBtn = findViewById(R.id.button_pick_datetime);
        scheduleBtn = findViewById(R.id.button_schedule);

        pickDateTimeBtn.setOnClickListener(v -> showDateTimePicker());

        scheduleBtn.setOnClickListener(v -> {
            String title = editInterviewTitle.getText().toString().trim();

            if (title.isEmpty() || scheduledDateTime.isEmpty()) {
                Toast.makeText(this, "Enter title and select date/time", Toast.LENGTH_SHORT).show();
                return;
            }
            RequestQueue queue = Volley.newRequestQueue(ScheduleInterviewActivity.this);
            String url = "http://192.168.43.224:5000/schedule_interview";

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("title", title);
                jsonBody.put("datetime", scheduledDateTime);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        Toast.makeText(this, "Scheduled Successfully", Toast.LENGTH_SHORT).show();
                    },
                    error -> {
                        Toast.makeText(this, "Failed to Schedule", Toast.LENGTH_SHORT).show();
                    });

            queue.add(request);

            InterviewDbHelper dbHelper = new InterviewDbHelper(ScheduleInterviewActivity.this);
            boolean success = dbHelper.insertInterview(title, scheduledDateTime);
            if (success) {
                Toast.makeText(this, "Interview saved locally!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error saving interview", Toast.LENGTH_SHORT).show();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            try {
                Date date = sdf.parse(scheduledDateTime);
                long triggerAtMillis = date.getTime();

                Intent alarmIntent = new Intent(this, InterviewAlarmReceiver.class);
                alarmIntent.putExtra("title", title);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                if (alarmManager != null) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(ScheduleInterviewActivity.this, InterviewAlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    ScheduleInterviewActivity.this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

        });
    }

    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            (view1, hourOfDay, minute) -> {
                                scheduledDateTime = dayOfMonth + "/" + (month + 1) + "/" + year +
                                        " " + hourOfDay + ":" + minute;
                                selectedDateTime.setText(scheduledDateTime);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
