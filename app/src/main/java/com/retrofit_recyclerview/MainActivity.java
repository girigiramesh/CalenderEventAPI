package com.retrofit_recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sun.bob.mcalendarview.MCalendarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button btn;
    private MCalendarView calender;
    String bearer = "Bearer G8rxSZFFdcaNqBCgaQSHvjoP-EcIfoZ14nVSoXOpzPXdv8in7EDIuoBPC6i71A1tw5G4h68uXJGGPkMpZizYdxJvJDt67_SCGC7KOILn2UcNU4l1LR26Aa1IotpUHI57QeSJHrfj-0vhj6WE6E8hxP0JgrTY0gG91IGohsqOgQOs3VIciYd8d60f5vhHvSu3PF__AMDV3EX8owt2ZUGsdzrg2YR0tswMaTn2YJEC5Qd_lRqu7mSZMi_Ch8NI96cidOGYcwbD211S9ZA8JPGMTWlky1SNSi8Yz31jw8QOJajT8pVW9K2wNJr3Vt7_mGUQoC-9ER2VSn1mqbWEeAevqJsFrVFsAJR93NIb5QBCKdZgygaNT91xDLXdSSFqJMk6ILNeqlxjuVe4eLvgMNdNZLUL_G2P1pppuGCVr4-cBBgTE_kMTVq38tLJdome7dwbvsplIIDCvrSIj3SvocHG8G4q6aPhzSiyNhe5AKDx11IkYguROcGaW5n-tt0w3BJkgdfbOyciedSrp1o0_1K74gvefZ91lSXPgZlJpjolu0RQvNUgiz-fbzcI-_52YYNN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        calender = ((MCalendarView) findViewById(R.id.calender));
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TrackervigilApi.BASE_URL)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TrackervigilApi trackervigilApi = retrofit.create(TrackervigilApi.class);

        trackervigilApi.getCalender(bearer, "School.Database").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body());
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jobj = jsonArray.getJSONObject(i);
                            String tempNotes = jobj.getString("holidayEventType");
                            if (tempNotes.equalsIgnoreCase("true")) {
                                String fromDate = jobj.getString("fromDate");
                                String toDate = jobj.getString("toDate");
                                Log.d(TAG, "XYZ>>>>>>: " + tempNotes + " " + fromDate.substring(0, 10) + " " + toDate.substring(0, 10));
                                String[] final_from_date = fromDate.substring(0, 10).split("-");
                                List<DataData> from_Date = new ArrayList<DataData>();
                                from_Date.add(new DataData(Integer.parseInt(final_from_date[0]), Integer.parseInt(final_from_date[1]), Integer.parseInt(final_from_date[2])));
                                for (int j = 0; j < from_Date.size(); j++) {
                                    calender.markDate(from_Date.get(j).getYear(), from_Date.get(j).getMonth(), from_Date.get(j).getDate());//mark multiple dates with this code.
                                }
                                String[] final_to_date = toDate.substring(0, 10).split("-");
                                List<DataData> to_Date = new ArrayList<DataData>();
                                to_Date.add(new DataData(Integer.parseInt(final_to_date[0]), Integer.parseInt(final_to_date[1]), Integer.parseInt(final_to_date[2])));
                                for (int k = 0; k < to_Date.size(); k++) {
//                                    calender.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                    calender.markDate(to_Date.get(k).getYear(), to_Date.get(k).getMonth(), to_Date.get(k).getDate());//mark multiple dates with this code.
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),"Dates are not available from DataBase:" , Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
