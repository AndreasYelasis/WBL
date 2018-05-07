package com.andreas.wbl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReportRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_register);
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
    }

    public void postReportToDB(View view) throws IOException {
        String postUrl = "http://DESKTOP-796HOHI/wbl_insert_reports.php?";

        EditText name_text = (EditText)findViewById(R.id.editTextReportName);
        String report_name_sting = name_text.getText().toString();
        EditText report_area_text = (EditText)findViewById(R.id.editTextReportArea);
        String report_area_sting = report_area_text.getText().toString();
        EditText report_phone_text = (EditText)findViewById(R.id.editTextReportPhone);
        String report_phone_string = report_phone_text.getText().toString();
        //int report_idcard_int = Integer.parseInt(report_idcard_string);
        EditText report_synergio_text = (EditText)findViewById(R.id.editTextReportSynergio);
        String report_synergio_sting = report_synergio_text.getText().toString();
        EditText report_address_text = (EditText)findViewById(R.id.editTextReportAddress);
        String report_address_string = report_address_text.getText().toString();
        EditText report_zipcode_text = (EditText)findViewById(R.id.editTextReportZipCode);
        String report_zipcode_string = report_zipcode_text.getText().toString();

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("customer_name",report_name_sting)
                .add("area",report_area_sting)
                .add("address",report_address_string)
                .add("phone",report_phone_string)
                .add("synergio",report_synergio_sting)
                .add("zipcode",report_zipcode_string)
                .build();

        Request request = new Request.Builder()
                .url(postUrl)
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
        Toast.makeText(getApplicationContext(),"Εγγραφή ολοκληρώθηκε...",Toast.LENGTH_LONG).show();
    }
}
