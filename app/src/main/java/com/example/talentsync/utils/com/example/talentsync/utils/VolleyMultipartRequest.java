package com.example.talentsync.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public abstract class VolleyMultipartRequest extends Request<NetworkResponse> {

    private final Response.Listener<NetworkResponse> mListener;
    private final Response.ErrorListener mErrorListener;
    private final Map<String, String> mHeaders;

    public VolleyMultipartRequest(int method, String url,
                                  Response.Listener<NetworkResponse> listener,
                                  Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mErrorListener = errorListener;
        this.mHeaders = null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data;boundary=" + boundary;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // text parameters
            Map<String, String> params = getParams();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    buildTextPart(bos, entry.getKey(), entry.getValue());
                }
            }

            // file parameters
            Map<String, DataPart> data = getByteData();
            if (data != null) {
                for (Map.Entry<String, DataPart> entry : data.entrySet()) {
                    buildFilePart(bos, entry.getKey(), entry.getValue());
                }
            }

            // end of multipart
            bos.write(("--" + boundary + "--").getBytes());
            return bos.toByteArray();

        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
            return null;
        }
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(response,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new com.android.volley.VolleyError(e));
        }
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(com.android.volley.VolleyError error) {
        mErrorListener.onErrorResponse(error);
    }

    /**
     * Override this method to pass files to upload
     */
    protected abstract Map<String, DataPart> getByteData() throws AuthFailureError;

    /**
     * Helper: boundary string
     */
    private final String boundary = "apiclient-" + System.currentTimeMillis();

    /**
     * Build text parameters
     */
    private void buildTextPart(ByteArrayOutputStream bos, String paramName, String value) throws IOException {
        bos.write(("--" + boundary + "\r\n").getBytes());
        bos.write(("Content-Disposition: form-data; name=\"" + paramName + "\"\r\n\r\n").getBytes());
        bos.write((value + "\r\n").getBytes());
    }

    /**
     * Build file part
     */
    private void buildFilePart(ByteArrayOutputStream bos, String paramName, DataPart fileData) throws IOException {
        bos.write(("--" + boundary + "\r\n").getBytes());
        bos.write(("Content-Disposition: form-data; name=\"" +
                paramName + "\"; filename=\"" + fileData.getFileName() + "\"\r\n").getBytes());
        bos.write(("Content-Type: " + fileData.getType() + "\r\n\r\n").getBytes());
        bos.write(fileData.getContent());
        bos.write("\r\n".getBytes());
    }

    /**
     * Helper class to encapsulate file data
     */
    public static class DataPart {
        private final String fileName;
        private final byte[] content;
        private final String type;

        public DataPart(String name, byte[] data) {
            this(name, data, "application/octet-stream");
        }

        public DataPart(String name, byte[] data, String type) {
            this.fileName = name;
            this.content = data;
            this.type = type;
        }

        public String getFileName() {
            return fileName;
        }

        public byte[] getContent() {
            return content;
        }

        public String getType() {
            return type;
        }
    }
}
