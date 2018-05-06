package com.andreas.wbl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class ModificationActivity extends AppCompatActivity {
    private List<Report> reports;
    private ReportsAdapter adapter;
    private GPSTracker gps;//create an object of class GPSTracker
    private Button buttonPhoto;
    private String encoded_string, image_name;
    private Bitmap bitmap;
    private File file;
    private Uri file_uri;

    String report_id_string="";
    String report_area_string="";
    String report_address_string="";
    String report_zip_code_string="";
    String report_customer_string="";
    String report_timestamp_taken_string="";
    String report_phone_string="";
    String report_synergio_string="";
    String report_thema_string="";
    String report_reason_string="";
    String report_action_string="";
    String report_diametros_string="";
    String report_type_string="";
    String report_damage_string="";
    String report_vathos_string="";
    String report_completed_string="";
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
        Bundle reportinfo = modificationIntent.getExtras();

        buttonPhoto = (Button) findViewById(R.id.buttonTakePicture);
        buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getFileUri();
                i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
                startActivityForResult(i, 10);
            }
        });

        EditText report_id_text = (EditText) findViewById(R.id.editTextReportId1);
        EditText report_area_text = (EditText) findViewById(R.id.editTextReportArea);
        EditText report_address_text = (EditText) findViewById(R.id.editTextReportAddress);
        EditText report_zip_code_text = (EditText) findViewById(R.id.editTextReportZipCode);
        EditText report_customer_name_text = (EditText) findViewById(R.id.editTextReportCustomerName);
        EditText report_timestamp_taken_text = (EditText) findViewById(R.id.editTextReportTimeStampTaken);
        EditText report_phone_text = (EditText) findViewById(R.id.editTextReportPhone);
        EditText report_synergio_text = (EditText) findViewById(R.id.editTextReportSynergio);

        Toast.makeText(getApplicationContext(), "From Intent", Toast.LENGTH_LONG).show();
            report_id_string = reportinfo.getString("report_id")+"";
            report_area_string = reportinfo.getString("area")+"";
            report_address_string = reportinfo.getString("address")+"";
            report_zip_code_string = reportinfo.getString("zip_code")+"";
            report_customer_string = reportinfo.getString("customer_name")+"";
            report_timestamp_taken_string = reportinfo.getString("timestamp_taken")+"";
            report_phone_string = reportinfo.getString("phone")+"";
            report_synergio_string = reportinfo.getString("synergio")+"";
            //emfanisi tou minimatos sta EditText
            report_id_text.setText(reportinfo.getString("report_id"));
            report_area_text.setText(reportinfo.getString("area"));
            report_address_text.setText(reportinfo.getString("address"));
            report_zip_code_text.setText(reportinfo.getString("zip_code"));
            report_customer_name_text.setText(reportinfo.getString("customer_name"));
            report_timestamp_taken_text.setText(reportinfo.getString("timestamp_taken"));
            report_phone_text.setText(reportinfo.getString("phone"));
            report_synergio_text.setText(reportinfo.getString("synergio"));

    }//end of onCreate

    public void postUpdatedReportToDB(View view) throws IOException {

        // Create class object
        gps = new GPSTracker(ModificationActivity.this);

        // Check if GPS enabled
        if(gps.canGetLocation()) {
            //methods getLatitude() and getLongitude()
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Toast.makeText(getApplicationContext(), "Συντεταγμένες - \nΓεωγραφικό Πλάτος: " + latitude + "\nΓεωγραφικό μήκος: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // Can't get location. GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            Toast.makeText(getApplicationContext(), "Οι Συντεταγμένες δεν έχουν καταχωριθεί\nΕνεργοποιήστε τον αισθητηρα τοποθεσείας", Toast.LENGTH_LONG).show();
            gps.showSettingsAlert();
        }

        String Url = "http://DESKTOP-796HOHI/wbl_update_reports.php?";
        EditText area_text = (EditText)findViewById(R.id.editTextReportArea);
        report_area_string = area_text.getText().toString();
        EditText address_text = (EditText)findViewById(R.id.editTextReportAddress);
        report_address_string = address_text.getText().toString();
        EditText zip_code_text = (EditText)findViewById(R.id.editTextReportZipCode);
        report_zip_code_string = zip_code_text.getText().toString();
        EditText customer_text = (EditText)findViewById(R.id.editTextReportCustomerName);
        report_customer_string = customer_text.getText().toString();
        EditText phone_text = (EditText)findViewById(R.id.editTextReportPhone);
        report_phone_string = phone_text.getText().toString();
        EditText synergio_text = (EditText)findViewById(R.id.editTextReportSynergio);
        report_synergio_string = synergio_text.getText().toString();
        EditText thema_text = (EditText)findViewById(R.id.editTextReportThema1);
        report_thema_string = thema_text.getText().toString();
        EditText report_reason_text = (EditText)findViewById(R.id.editTextReportReason1);
        report_reason_string = report_reason_text.getText().toString();
        EditText report_action_text = (EditText)findViewById(R.id.editTextReportAction1);
        report_action_string = report_action_text.getText().toString();
        EditText report_diametros_text = (EditText)findViewById(R.id.editTextReportDiametros1);
        report_diametros_string = report_diametros_text.getText().toString();
        EditText report_type_text = (EditText)findViewById(R.id.editTextReportType1);
        report_type_string = report_type_text.getText().toString();
        EditText report_damage_text = (EditText)findViewById(R.id.editTextReportDamage1);
        report_damage_string = report_damage_text.getText().toString();
        EditText report_vathos_text = (EditText)findViewById(R.id.editTextReportVathos1);
        report_vathos_string = report_vathos_text.getText().toString();
        EditText report_completed_text = (EditText)findViewById(R.id.editTextReportCompleted1);
        report_completed_string = report_completed_text.getText().toString();

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("report_id",report_id_string)
                .add("area",report_area_string)
                .add("address",report_address_string)
                .add("zip_code",report_zip_code_string)
                .add("customer_name",report_customer_string)
                .add("phone",report_phone_string)
                .add("synergio",report_synergio_string)
                .add("thema",report_thema_string)
                .add("reason",report_reason_string)
                .add("action",report_action_string)
                .add("diametros",report_diametros_string)
                .add("type",report_type_string)
                .add("damage",report_damage_string)
                .add("vathos",report_vathos_string)
                .add("lat",gps.getLatitude()+"")
                .add("lon",gps.getLongitude()+"")
                .add("completed",report_completed_string)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
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
//σταρτ
    private void getFileUri() {


    SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyHHmmss");
    String format = s.format(new Date());
//        image_name = "pellaes.jpg";
        image_name = format+".jpg";
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + image_name
        );

        file_uri = Uri.fromFile(file);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK) {
            new Encode_image().execute();
        }
    }

    private class Encode_image extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            bitmap = BitmapFactory.decodeFile(file_uri.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            bitmap.recycle();

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            makeRequest();
        }
    }

    private void makeRequest() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, "http://DESKTOP-796HOHI/connection.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
//                map.put("report_id",report_id);
                map.put("encoded_string",encoded_string);
                map.put("image_name",image_name);

                return map;
            }
        };
        requestQueue.add(request);
    }
    //φινιση
}
