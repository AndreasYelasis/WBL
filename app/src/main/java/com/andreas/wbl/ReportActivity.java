package com.andreas.wbl;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Andreas on 11/03/2018.
 */

public class ReportActivity extends AppCompatActivity {

    private static final String TAG = ReportActivity.class.getSimpleName();
    private List<Report> reports;
    private RecyclerView recyclerView;
    private LinearLayoutManager LinearLayout;
    private ReportsAdapter adapter;
    public static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "25830000", null)));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //declare of buttons
        final Button buttonShowReport = (Button) findViewById(R.id.buttonShowReport);
        final Button buttonSearchReport = (Button) findViewById(R.id.buttonReportSearch);
        final Button buttonUncompletedReport = (Button) findViewById(R.id.buttonUncompletedReport);
        //on click listener functions
        buttonShowReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reports.clear();
                Toast.makeText(ReportActivity.this, "Show All",Toast.LENGTH_LONG).show();
                url="http://DESKTOP-796HOHI/wbl_get_reports.php?id=";
                getReportsFromDB(0);
            }
        });
        buttonSearchReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reports.clear();
                Toast.makeText(ReportActivity.this, "Search",Toast.LENGTH_LONG).show();
                url="http://DESKTOP-796HOHI/wbl_search_reports.php?id=";
                searchReportsFromDB(0);
            }
        });
        buttonUncompletedReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reports.clear();
                Toast.makeText(ReportActivity.this, "Uncompleted",Toast.LENGTH_LONG).show();
                url="http://DESKTOP-796HOHI/wbl_get_uncompleted_reports.php?id=";
                getUncompletedReportsFromDB(0);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        reports = new ArrayList<>();

        LinearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LinearLayout);

        adapter = new ReportsAdapter(this, reports);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (LinearLayout.findLastCompletelyVisibleItemPosition() == reports.size() - 1) {
                    getReportsFromDB(reports.get(reports.size() - 1).getReportId());
                }

            }
        });
    }

    private void getUncompletedReportsFromDB(int id) {

        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... reportIds) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url + reportIds[0])
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Report report = new Report(
                                object.getInt("report_id"),
                                object.getString("area"),
                                object.getString("address"),
                                object.getInt("zip_code"),
                                object.getString("customer_name"),
                                object.getString("timestamp_taken"),
                                object.getInt("phone"),
                                object.getString("synergio"),
                                object.getString("timestamp_completed"),
                                object.getString("thema"),
                                object.getString("reason"),
                                object.getString("action"),
                                object.getString("diametros"),
                                object.getString("type"),
                                object.getString("damage"),
                                object.getInt("vathos"),
                                object.getString("photo"),
                                object.getInt("lat"),
                                object.getInt("lon"),
                                object.getInt("completed"));

                        ReportActivity.this.reports.add(report);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };
        asyncTask.execute(id);
    }

    private void searchReportsFromDB( int id ) {

        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            EditText id_search = (EditText)findViewById(R.id.editTextIdSearch);
            String report_id_search = id_search.getText().toString();
            @Override
            protected Void doInBackground(Integer... reportIds) {

                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .add("report_id",report_id_search)
                        .build();

                Request request = new Request.Builder()
                        .url(url + reportIds[0])
                        .post(formBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Report report = new Report(
                                object.getInt("report_id"),
                                object.getString("area"),
                                object.getString("address"),
                                object.getInt("zip_code"),
                                object.getString("customer_name"),
                                object.getString("timestamp_taken"),
                                object.getInt("phone"),
                                object.getString("synergio"),
                                object.getString("timestamp_completed"),
                                object.getString("thema"),
                                object.getString("reason"),
                                object.getString("action"),
                                object.getString("diametros"),
                                object.getString("type"),
                                object.getString("damage"),
                                object.getInt("vathos"),
                                object.getString("photo"),
                                object.getInt("lat"),
                                object.getInt("lon"),
                                object.getInt("completed"));

                        ReportActivity.this.reports.add(report);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        asyncTask.execute(id);
    }

    private void getReportsFromDB(int id) {

        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... reportIds) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url + reportIds[0])
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Report report = new Report(
                                object.getInt("report_id"),
                                object.getString("area"),
                                object.getString("address"),
                                object.getInt("zip_code"),
                                object.getString("customer_name"),
                                object.getString("timestamp_taken"),
                                object.getInt("phone"),
                                object.getString("synergio"),
                                object.getString("timestamp_completed"),
                                object.getString("thema"),
                                object.getString("reason"),
                                object.getString("action"),
                                object.getString("diametros"),
                                object.getString("type"),
                                object.getString("damage"),
                                object.getInt("vathos"),
                                object.getString("photo"),
                                object.getInt("lat"),
                                object.getInt("lon"),
                                object.getInt("completed"));

                        ReportActivity.this.reports.add(report);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        asyncTask.execute(id);
    }

}
