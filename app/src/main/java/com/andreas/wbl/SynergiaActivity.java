package com.andreas.wbl;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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


public class SynergiaActivity extends AppCompatActivity {

/**
 * Created by Andreas on 11/03/2018.
 */


    private static final String TAG = SynergiaActivity.class.getSimpleName();
    private List<Synergia> synergias;
    private RecyclerView recyclerView;
    private LinearLayoutManager LinearLayout;
    private SynergiasAdapter adapter;
    public static String url="http://DESKTOP-796HOHI/wbl_get_synergias.php?id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synergia);
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

        final Button buttonSynergiaSearch = (Button) findViewById(R.id.buttonSynergiaSearch);

        buttonSynergiaSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(SynergiaActivity.this, "Search",Toast.LENGTH_LONG).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        synergias = new ArrayList<>();
        getSynergiasFromDB(0);
        //searchSynergiasFromDB(0);
        LinearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LinearLayout);

        adapter = new SynergiasAdapter(this, synergias);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (LinearLayout.findLastCompletelyVisibleItemPosition() == synergias.size() - 1) {
                    getSynergiasFromDB(synergias.get(synergias.size() - 1).getId());
                }
            }
        });

    }

    private void getSynergiasFromDB(int id ) {
        //AsyncTask enables proper and easy use of the UI thread.
        //This class allows you to perform background operations and publish results on the UI thread without having to manipulate threads and/or handlers.
        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            // Perform an operation on a background thread
            @Override
            protected Void doInBackground(Integer... synergiaIds) {//Params:Type of data passed to the task upon execution, i.e., arguments passed to execute() and accepted by doInBackground().
                //OkHttp is an HTTP client. I create an object of OkHttpClient class named client
                OkHttpClient client = new OkHttpClient();
                //First, we must instantiate an OkHttpClient and create a Request object.
                Request request = new Request.Builder()//I create new object named request
                        .url(url + synergiaIds[0])
                        .build();//OkHttp requires Java 7 to build and run tests.
                try {//Synchronous Network Calls
                    Response response = client.newCall(request).execute();
                    //Because Android disallows network calls on the main thread, you can only make synchronous calls if you do so on a separate thread or a background service.
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Synergia synergia = new Synergia(
                                object.getInt("synergio_id"),
                                object.getString("synergio_name")
                                /*object.getBoolean("epistatis")*/);

                        SynergiaActivity.this.synergias.add(synergia);
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
