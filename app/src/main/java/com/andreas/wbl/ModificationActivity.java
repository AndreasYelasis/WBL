package com.andreas.wbl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

public class ModificationActivity extends AppCompatActivity {

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
