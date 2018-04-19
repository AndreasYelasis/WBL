package com.andreas.wbl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent intent = getIntent();
        Integer position=intent.getIntExtra("thesi",0);
        Toast.makeText(this, "Position is: " + position,Toast.LENGTH_LONG).show();
    }
}
