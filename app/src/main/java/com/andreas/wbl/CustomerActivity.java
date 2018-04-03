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

public class CustomerActivity extends AppCompatActivity {

    private static final String TAG = CustomerActivity.class.getSimpleName();
    private List<Customer> customers;
    private RecyclerView recyclerView;
    private LinearLayoutManager LinearLayout;
    private CustomersAdapter adapter;
    public static String url="http://DESKTOP-796HOHI/wbl_get_customers.php?id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
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

        final Button buttonShowCust = (Button) findViewById(R.id.buttonShowCust);
        final Button buttonSearchCust = (Button) findViewById(R.id.buttonCustomerSearch);
        buttonShowCust.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(CustomerActivity.this, "Show",Toast.LENGTH_LONG).show();
                url="http://DESKTOP-796HOHI/wbl_get_customers.php?id=";
            }
        });
        buttonSearchCust.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(CustomerActivity.this, "Search",Toast.LENGTH_LONG).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        customers = new ArrayList<>();
        getCustomersFromDB(0);
        //searchCustomersFromDB(0);
        LinearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LinearLayout);

        adapter = new CustomersAdapter(this, customers);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (LinearLayout.findLastCompletelyVisibleItemPosition() == customers.size() - 1) {
                    getCustomersFromDB(customers.get(customers.size() - 1).getId());
                }

            }
        });

    }

    public void registerCustomer(View view){
        Intent intent = new Intent(this, CustomerRegisterActivity.class);
        startActivity(intent);
    }

    private void searchCustomersFromDB( int id ) {
        {
            EditText search_idcard_text = (EditText)findViewById(R.id.editTextIdSearch);
            final String search_idcard_string = search_idcard_text.getText().toString();
            AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
                @Override
                protected Void doInBackground(Integer... customerIds) {
                    ////

                    OkHttpClient client = new OkHttpClient();

                    RequestBody formBody = new FormBody.Builder()
                            .add("idcard",search_idcard_string)
                            .build();

                    Request request = new Request.Builder()
                            .url("http://DESKTOP-796HOHI/wbl_search_customers.php?id=")
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
                    ////

//                    try {
//                        Response response = client.newCall(request).execute();
//
//                        JSONArray array = new JSONArray(response.body().string());
//
//                        for (int i = 0; i < array.length(); i++) {
//
//                            JSONObject object = array.getJSONObject(i);
//
//                            Customer customer = new Customer(
//                                    object.getInt("id"),
//                                    object.getString("customer_name"),
//                                    object.getString("customer_surname"),
//                                    object.getInt("customer_id_card"),
//                                    object.getInt("customer_birthdate"),
//                                    object.getString("customer_address"),
//                                    object.getInt("customer_zip_code"));
//
//                            CustomerActivity.this.customers.add(customer);
//                        }
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
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

    private void getCustomersFromDB(int id ) {

        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... customerIds) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url + customerIds[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Customer customer = new Customer(
                                object.getInt("id"),
                                object.getString("customer_name"),
                                object.getString("customer_surname"),
                                object.getInt("customer_id_card"),
                                object.getInt("customer_birthdate"),
                                object.getString("customer_address"),
                                object.getInt("customer_zip_code"));

                        CustomerActivity.this.customers.add(customer);
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
