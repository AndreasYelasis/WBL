package com.andreas.wbl;

import android.widget.AdapterView.OnItemSelectedListener;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ModificationActivity extends AppCompatActivity {
    private List<Report> reports;
    private ReportsAdapter adapter;
    private GPSTracker gps;//create an object of class GPSTracker
    private Button buttonPhoto;
    private String encoded_string, image_name;
    private Bitmap bitmap;
    private File file;
    private Uri file_uri;
    private Spinner spinnerThema;
    private Spinner spinnerReason;
    private Spinner spinnerAction;
    private Spinner spinnerDiametros;
    private Spinner spinnerType;
    private Spinner spinnerDamage;
    private Integer flagReason=0;
    private Integer flagAction=0;
    private Integer flagThema=0;
    private Integer flagDiametros=0;
    private Integer flagType=0;
    private Integer flagDamage=0;

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
        EditText report_thema_text = (EditText) findViewById(R.id.editTextReportThema1);
        EditText report_reason_text = (EditText) findViewById(R.id.editTextReportReason1);
        EditText report_action_text = (EditText) findViewById(R.id.editTextReportAction1);
        EditText report_diametros_text = (EditText) findViewById(R.id.editTextReportDiametros1);
        EditText report_type_text = (EditText) findViewById(R.id.editTextReportType1);
        EditText report_damage_text = (EditText) findViewById(R.id.editTextReportDamage1);
        EditText report_vathos_text = (EditText) findViewById(R.id.editTextReportVathos1);

        report_id_string = reportinfo.getString("report_id")+"";
        report_area_string = reportinfo.getString("area")+"";
        report_address_string = reportinfo.getString("address")+"";
        report_zip_code_string = reportinfo.getString("zip_code")+"";
        report_customer_string = reportinfo.getString("customer_name")+"";
        report_timestamp_taken_string = reportinfo.getString("timestamp_taken")+"";
        report_phone_string = reportinfo.getString("phone")+"";
        report_synergio_string = reportinfo.getString("synergio")+"";
        report_thema_string = reportinfo.getString("thema")+"";
        report_reason_string = reportinfo.getString("reason")+"";
        report_action_string = reportinfo.getString("action")+"";
        report_diametros_string = reportinfo.getString("diametros")+"";
        report_type_string = reportinfo.getString("type")+"";
        report_damage_string = reportinfo.getString("damage")+"";
        report_vathos_string = reportinfo.getString("vathos")+"";
        //emfanisi tou minimatos sta EditText
        report_id_text.setText(reportinfo.getString("report_id"));
        report_area_text.setText(reportinfo.getString("area"));
        report_address_text.setText(reportinfo.getString("address"));
        report_zip_code_text.setText(reportinfo.getString("zip_code"));
        report_customer_name_text.setText(reportinfo.getString("customer_name"));
        report_timestamp_taken_text.setText(reportinfo.getString("timestamp_taken"));
        report_phone_text.setText(reportinfo.getString("phone"));
        report_synergio_text.setText(reportinfo.getString("synergio"));
        report_thema_text.setText(reportinfo.getString("thema"));
        report_reason_text.setText(reportinfo.getString("reason"));
        report_action_text.setText(reportinfo.getString("action"));
        report_diametros_text.setText(reportinfo.getString("diametros"));
        report_type_text.setText(reportinfo.getString("type"));
        report_damage_text.setText(reportinfo.getString("damage"));
        report_vathos_text.setText(reportinfo.getString("vathos"));

        addListenerOnSpinnerItemSelection();
        addItemsOnSpinnerThema();
        addItemsOnSpinnerReason();
        addItemsOnSpinnerAction();
        addItemsOnSpinnerDiametros();
        addItemsOnSpinnerType();
        addItemsOnSpinnerDamage();
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
                map.put("report_id",report_id_string);
                map.put("encoded_string",encoded_string);
                map.put("image_name",image_name);

                return map;
            }
        };
        requestQueue.add(request);
    }
    //φινιση
    public void addListenerOnSpinnerItemSelection() {
        spinnerThema = (Spinner) findViewById(R.id.spinnerThema);
        spinnerThema.setOnItemSelectedListener(new CustomOnItemSelectedListenerThema());
        spinnerReason = (Spinner) findViewById(R.id.spinnerReason);
        spinnerReason.setOnItemSelectedListener(new CustomOnItemSelectedListenerReason());
        spinnerAction = (Spinner) findViewById(R.id.spinnerAction);
        spinnerAction.setOnItemSelectedListener(new CustomOnItemSelectedListenerAction());
        spinnerDiametros = (Spinner) findViewById(R.id.spinnerDiametros);
        spinnerDiametros.setOnItemSelectedListener(new CustomOnItemSelectedListenerDiametros());
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        spinnerType.setOnItemSelectedListener(new CustomOnItemSelectedListenerType());
        spinnerDamage = (Spinner) findViewById(R.id.spinnerDamage);
        spinnerDamage.setOnItemSelectedListener(new CustomOnItemSelectedListenerDamage());
    }
    //add items into spinner dynamically
    //spinner Thema
    public void addItemsOnSpinnerThema() {

        spinnerThema = (Spinner) findViewById(R.id.spinnerThema);
        List<String> listThema = new ArrayList<String>();
        listThema.add("Διαρροή νερού στο υποστατικό");
        listThema.add("Διαρροή νερού από δίκτυο");
        listThema.add("Αφανής διαρροή");
        listThema.add("Δεν έχει νερό");
        listThema.add("Χαμηλή Πίεση");
        listThema.add("Ψηλή Πίεση");
        listThema.add("Ποιότητα νερού");
        listThema.add("Σπατάλη νερού");
        listThema.add("Σπασμένος Υδρομετρητής");
        listThema.add("Δυσκολία Καταγραφής Υδρομετρητή");
        listThema.add("Διάφορα");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listThema);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThema.setAdapter(dataAdapter);
    }
    public class CustomOnItemSelectedListenerThema implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            flagThema++;
            if (flagThema!=1){
            Toast.makeText(parent.getContext(),"Θέμα : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
                report_thema_string = parent.getItemAtPosition(pos).toString();
                EditText report_thema_text = (EditText) findViewById(R.id.editTextReportThema1);
                report_thema_text.setText(parent.getItemAtPosition(pos).toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }
    //spinner reason
    public void addItemsOnSpinnerReason() {

        spinnerReason = (Spinner) findViewById(R.id.spinnerReason);
        List<String> listReason = new ArrayList<String>();
        listReason.add("Κεντρικός αγωγός");
        listReason.add("Παροχή υποστατικού");
        listReason.add("Διαρροή δικλείδας");
        listReason.add("Διαρροή υδροστομίου πυρόσβεσης");
        listReason.add("Βλαβη ιδιοκτητη");
        listReason.add("Άλλη");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listReason);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReason.setAdapter(dataAdapter);
    }
    public class CustomOnItemSelectedListenerReason implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            flagReason++;
            if (flagReason!=1){
                Toast.makeText(parent.getContext(),"Λόγος : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();
                report_reason_string = parent.getItemAtPosition(pos).toString();
                EditText report_reason_text = (EditText) findViewById(R.id.editTextReportReason1);
                report_reason_text.setText(parent.getItemAtPosition(pos).toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }
    //spinner action
    public void addItemsOnSpinnerAction() {

        spinnerAction = (Spinner) findViewById(R.id.spinnerAction);
        List<String> listAction = new ArrayList<String>();
        listAction.add("Αντικατάσταση");
        listAction.add("Ανύψωση");
        listAction.add("Μεταφορά");
        listAction.add("Καταγράφει κανονικά");
        listAction.add("Επιδιόρθωση");
        listAction.add("Ενημέρωση ιδιοκτήτη");
        listAction.add("Άνοιγμα διακόπτη και ενημέρωση ιδιοκτήτη");
        listAction.add("Άλλη");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listAction);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAction.setAdapter(dataAdapter);
    }
    public class CustomOnItemSelectedListenerAction implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            flagAction++;
            if (flagAction!=1){
                Toast.makeText(parent.getContext(),"Ενέργεια : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();
                report_action_string = parent.getItemAtPosition(pos).toString();
                EditText report_action_text = (EditText) findViewById(R.id.editTextReportAction1);
                report_action_text.setText(parent.getItemAtPosition(pos).toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }
    //spinner diametros
    public void addItemsOnSpinnerDiametros() {

        spinnerDiametros = (Spinner) findViewById(R.id.spinnerDiametros);
        List<String> listDiametros = new ArrayList<String>();
        listDiametros.add("2 ίντζεσ");
        listDiametros.add("3 ίντζεσ");
        listDiametros.add("4 ίντζεσ");
        listDiametros.add("6 ίντζεσ");
        listDiametros.add("8 ίντζεσ");
        listDiametros.add("63mm");
        listDiametros.add("90mm");
        listDiametros.add("125mm");
        listDiametros.add("180mm");
        listDiametros.add("225mm");
        listDiametros.add("Άλλη");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listDiametros);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiametros.setAdapter(dataAdapter);
    }
    public class CustomOnItemSelectedListenerDiametros implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            flagDiametros++;
            if (flagDiametros!=1){
                Toast.makeText(parent.getContext(),"Διάμετρος σωλήνας : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();
                report_diametros_string = parent.getItemAtPosition(pos).toString();
                EditText report_diametros_text = (EditText) findViewById(R.id.editTextReportDiametros1);
                report_diametros_text.setText(parent.getItemAtPosition(pos).toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }
    //spinner type
    public void addItemsOnSpinnerType() {

        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        List<String> listType = new ArrayList<String>();
        listType.add("UPVC");
        listType.add("HPPE");
        listType.add("GI");
        listType.add("Steel");
        listType.add("DI");
        listType.add("MDPE μαύρος");
        listType.add("HDPE");
        listType.add("UPVC");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listType);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(dataAdapter);
    }
    public class CustomOnItemSelectedListenerType implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            flagType++;
            if (flagType!=1){
                Toast.makeText(parent.getContext(),"Είδος σωλήνας : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();
                report_type_string = parent.getItemAtPosition(pos).toString();
                EditText report_type_text = (EditText) findViewById(R.id.editTextReportType1);
                report_type_text.setText(parent.getItemAtPosition(pos).toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }
    //spinner damage
    public void addItemsOnSpinnerDamage() {

        spinnerDamage = (Spinner) findViewById(R.id.spinnerDamage);
        List<String> listDamage = new ArrayList<String>();
        listDamage.add("Φθορά διακόπτη πεζοδρομίου");
        listDamage.add("Ρίζες δέντρου");
        listDamage.add("Φθορά σαλαμάστρας");
        listDamage.add("Φθορά μπουλωνιών");
        listDamage.add("Μπουλώνια φλάντζας");
        listDamage.add("Οξείδωση");
        listDamage.add("Διαμήκης θραύση σωλήνα");
        listDamage.add("Θραύση ένωσης - στο φερούλι");
        listDamage.add("Θραύση ένωσης - στο διακόπτη");
        listDamage.add("Θραύση ένωσης - σε εξάρτημα");
        listDamage.add("Φθορά φερουλιού");
        listDamage.add("Σέλλα - λάστιχο Ο Ring");
        listDamage.add("Φθορά διακόπτη σέλλας");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listDamage);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDamage.setAdapter(dataAdapter);
    }
    public class CustomOnItemSelectedListenerDamage implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            flagDamage++;
            if (flagDamage!=1){
                Toast.makeText(parent.getContext(),"Ζημιά : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_SHORT).show();
                report_damage_string = parent.getItemAtPosition(pos).toString();
                EditText report_damage_text = (EditText) findViewById(R.id.editTextReportDamage1);
                report_damage_text.setText(parent.getItemAtPosition(pos).toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }

    }

}
