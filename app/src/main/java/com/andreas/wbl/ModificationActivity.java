package com.andreas.wbl;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModificationActivity extends AppCompatActivity {
    private List<Report> reports;
    private ReportsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);
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

        Intent modificationIntent = getIntent();
        Bundle info=getIntent().getExtras();

        EditText report_id_text = (EditText) findViewById(R.id.editTextReportId1);
        EditText report_area_text = (EditText) findViewById(R.id.editTextReportArea);
        EditText report_address_text = (EditText) findViewById(R.id.editTextReportAddress);
        EditText report_zip_code_text = (EditText) findViewById(R.id.editTextReportZipCode);
        EditText report_customer_name_text = (EditText) findViewById(R.id.editTextReportCustomerName);
        EditText report_timestamp_taken_text = (EditText) findViewById(R.id.editTextReportTimeStampTaken);
        EditText report_phone_text = (EditText) findViewById(R.id.editTextReportPhone);
        EditText report_synergio_text = (EditText) findViewById(R.id.editTextReportSynergio);

        if (info != null) {
        String report_id_string = modificationIntent.getStringExtra("report_id");
        String report_area_string = modificationIntent.getStringExtra("area");
        String report_address_string = modificationIntent.getStringExtra("address");
        String report_zip_code_string = modificationIntent.getStringExtra("zip_code");
        String report_customer_string = modificationIntent.getStringExtra("customer_name");
        String report_timestamp_taken_string = modificationIntent.getStringExtra("timestamp_taken");
        String report_phone_string = modificationIntent.getStringExtra("phone");
        String report_synergio_string = modificationIntent.getStringExtra("synergio");

        report_id_text.setText(modificationIntent.getStringExtra("report_id"));
        report_area_text.setText(modificationIntent.getStringExtra("area"));
        report_address_text.setText(modificationIntent.getStringExtra("address"));
        report_zip_code_text.setText(modificationIntent.getStringExtra("zip_code"));
        report_customer_name_text.setText(modificationIntent.getStringExtra("customer_name"));
        report_timestamp_taken_text.setText(modificationIntent.getStringExtra("timestamp_taken"));
        report_phone_text.setText(modificationIntent.getStringExtra("phone"));
        report_synergio_text.setText(modificationIntent.getStringExtra("synergio"));}



//        getReportsFromDB(0);
    }

    private void getReportsFromDB(int id) {
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
                        .url("http://DESKTOP-796HOHI/wbl_search_reports.php?id=" + reportIds[0])
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

                        ModificationActivity.this.reports.add(report);
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

    public void postUpdatedReportToDB1(View view) throws IOException {
        String Url = "http://DESKTOP-796HOHI/wbl_update_reports.php?";

        EditText report_id_text = (EditText)findViewById(R.id.editTextReportId1);
        String report_id_string = report_id_text.getText().toString();
        EditText thema_text = (EditText)findViewById(R.id.editTextReportThema1);
        String report_thema_string = thema_text.getText().toString();
        EditText report_reason_text = (EditText)findViewById(R.id.editTextReportReason1);
        String report_reason_string = report_reason_text.getText().toString();
        EditText report_action_text = (EditText)findViewById(R.id.editTextReportAction1);
        String report_action_string = report_action_text.getText().toString();
        EditText report_diametros_text = (EditText)findViewById(R.id.editTextReportDiametros1);
        String report_diametros_string = report_diametros_text.getText().toString();
        EditText report_type_text = (EditText)findViewById(R.id.editTextReportType1);
        String report_type_string = report_type_text.getText().toString();
        EditText report_damage_text = (EditText)findViewById(R.id.editTextReportDamage1);
        String report_damage_string = report_damage_text.getText().toString();
        EditText report_vathos_text = (EditText)findViewById(R.id.editTextReportVathos1);
        String report_vathos_string = report_vathos_text.getText().toString();
        EditText report_completed_text = (EditText)findViewById(R.id.editTextReportCompleted1);
        String report_completed_string = report_completed_text.getText().toString();

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("report_id",report_id_string)
                .add("thema",report_thema_string)
                .add("reason",report_reason_string)
                .add("action",report_action_string)
                .add("diametros",report_diametros_string)
                .add("type",report_type_string)
                .add("damage",report_damage_string)
                .add("vathos",report_vathos_string)
                .add("completed",report_completed_string)
                .build();

        Request request = new Request.Builder()
                .url(Url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("TAG", response.body().string());
            }
        });
        Toast.makeText(getApplicationContext(),"Update Sent...",Toast.LENGTH_LONG).show();
    }
}
