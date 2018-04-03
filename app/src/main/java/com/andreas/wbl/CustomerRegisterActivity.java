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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomerRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
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

    public void postCustomerToDB(View view) throws IOException {
        String postUrl = "http://DESKTOP-796HOHI/wbl_insert_customers.php?";

        EditText name_text = (EditText)findViewById(R.id.editTextCustomerName);
        String customer_name_sting = name_text.getText().toString();
        EditText customer_surname_text = (EditText)findViewById(R.id.editTextCustomerSurname);
        String customer_surname_sting = customer_surname_text.getText().toString();
        EditText customer_idcard_text = (EditText)findViewById(R.id.editTextCustomerIdCard);
        String customer_idcard_string = customer_idcard_text.getText().toString();
        //int customer_idcard_int = Integer.parseInt(customer_idcard_string);
        EditText customer_birthdate_text = (EditText)findViewById(R.id.editTextCustomerBirthdate);
        String customer_birthdate_sting = customer_birthdate_text.getText().toString();
        EditText customer_address_text = (EditText)findViewById(R.id.editTextCustomerAddress);
        String customer_address_string = customer_address_text.getText().toString();
        EditText customer_zipcode_text = (EditText)findViewById(R.id.editTextCustomerZipCode);
        String customer_zipcode_string = customer_zipcode_text.getText().toString();

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                //.add("id","")
                .add("name",customer_name_sting)
                .add("surname",customer_surname_sting)
                .add("idcard",customer_idcard_string)
                .add("birthdate",customer_birthdate_sting)
                .add("address",customer_address_string)
                .add("zipcode",customer_zipcode_string)
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
    }
}
