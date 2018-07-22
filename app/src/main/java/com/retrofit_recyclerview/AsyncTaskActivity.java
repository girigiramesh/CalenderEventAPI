package com.retrofit_recyclerview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {
    Button tv;
    String grantType = "password";
    String email = "moiz.nixfi@gmail.com";
    String password = "Password@1";
    String domainName = "School.Database";
    String contentType = "application/x-www-form-urlencoded";
    String bearer = "Bearer G8rxSZFFdcaNqBCgaQSHvjoP-EcIfoZ14nVSoXOpzPXdv8in7EDIuoBPC6i71A1tw5G4h68uXJGGPkMpZizYdxJvJDt67_SCGC7KOILn2UcNU4l1LR26Aa1IotpUHI57QeSJHrfj-0vhj6WE6E8hxP0JgrTY0gG91IGohsqOgQOs3VIciYd8d60f5vhHvSu3PF__AMDV3EX8owt2ZUGsdzrg2YR0tswMaTn2YJEC5Qd_lRqu7mSZMi_Ch8NI96cidOGYcwbD211S9ZA8JPGMTWlky1SNSi8Yz31jw8QOJajT8pVW9K2wNJr3Vt7_mGUQoC-9ER2VSn1mqbWEeAevqJsFrVFsAJR93NIb5QBCKdZgygaNT91xDLXdSSFqJMk6ILNeqlxjuVe4eLvgMNdNZLUL_G2P1pppuGCVr4-cBBgTE_kMTVq38tLJdome7dwbvsplIIDCvrSIj3SvocHG8G4q6aPhzSiyNhe5AKDx11IkYguROcGaW5n-tt0w3BJkgdfbOyciedSrp1o0_1K74gvefZ91lSXPgZlJpjolu0RQvNUgiz-fbzcI-_52YYNN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        tv = (Button) findViewById(R.id.tv);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        new AsyncTaskClass(this).execute();
    }

    public class AsyncTaskClass extends AsyncTask<String, Void, String> {

        private static final String TAG = "ContactsAsyncTask";
        private final Context mContext;
        private int responseCode;
        String displayMessage = null;

        public AsyncTaskClass(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG, "onPreExecute()");
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = useHttpurl(strings);
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String username = jsonObject.getString("userName");
                    Log.d(TAG, "onPostExecute: " + username);
                    Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private String useHttpurl(String[] inputs) {
            String responseString = "";
            try {
                // Get the contacts information
                URL url = new URL("http://52.183.29.32/edunix/TOKEN");
//                URL url = new URL("http://52.183.29.32/edunix/api/HolidayEvent");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(60000);
                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.connect();

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write("");
                // Send require parameters to the Service call .
                 writer.write(getPostDataString(getParamsInHashMap(contentType, grantType, email, password, domainName)));
//                 writer.write(getPostDataString(getParamsInHash(bearer, domainName)));
                writer.flush();
                writer.close();
                outputStream.close();
                responseCode = connection.getResponseCode();
                InputStream inputStream = connection.getInputStream();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((line = bufferedReader.readLine()) != null) {
                        responseString += line;
                    }
                } else {
                    responseString = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseString;
        }

        private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                result.append("&");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return result.toString();
        }

        private HashMap<String, String> getParamsInHashMap(String contentType, String grantType, String email, String password ,String domainName) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", contentType);
            params.put("grant_type", grantType);
            params.put("username", email);
            params.put("password", password);
            params.put("domain-name", domainName);
            return params;
        }

        private HashMap<String, String> getParamsInHash(String authorization ,String domainName) {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("Authorization", authorization);
            params.put("domain-name", domainName);
            return params;
        }
    }
}
