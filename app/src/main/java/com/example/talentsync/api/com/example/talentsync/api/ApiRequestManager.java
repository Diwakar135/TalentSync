package com.example.talentsync.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ApiRequestManager {

    public interface AqiResponseListener {
        void onSuccess(JSONObject aqiData);
        void onError(String message);
    }

    private static final String API_KEY = "cbc6f61155603b07f217c678e945855798f163a9"; // Replace with your actual AQI API key
    private static final String BASE_URL = "https://api.waqi.info/feed/";

    private final Context context;

    public ApiRequestManager(Context context) {
        this.context = context;
    }

    public void getAqiData(String city, AqiResponseListener listener) {
        String url = BASE_URL + city + "/?token=" + API_KEY;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                listener::onSuccess,
                error -> listener.onError(error.toString())
        );

        Volley.newRequestQueue(context).add(jsonRequest);
    }
}
