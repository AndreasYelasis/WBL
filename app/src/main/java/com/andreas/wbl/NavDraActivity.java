package com.andreas.wbl;

import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class NavDraActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_dra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "25830000", null)));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        WebView browser = (WebView) findViewById(R.id.webView);//i leksi browser ego tin onomazo etsi mporei na einai Andreas
        browser.setWebViewClient(new WebViewClient());//tha fortoso mesa sto programma istoselida
        //browser.setWebChromeClient(new WebChromeClient());//anoigei to google chrome san external efarmogi
        browser.loadUrl("https://www.cyprus-weather.org/");//mia istoselida gia na fortothei prepei na graftei to programa epikinonias. to kano oso pio aplo mporo gia na grapsei mono tin istoselida. An thelo evaza kai .com

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_dra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            Intent intent = new Intent(this,InfoActivity.class);
            startActivity(intent);
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new_report) {
            Intent newregisterIntent = new Intent(this,ReportRegisterActivity.class);
            startActivity(newregisterIntent);
        } else if (id == R.id.nav_peristatika) {
            Intent reportIntent = new Intent(this,ReportActivity.class);
            startActivity(reportIntent);
        } else if (id == R.id.nav_synergia) {
            Intent synergiaIntent = new Intent(this,SynergiaActivity.class);
            startActivity(synergiaIntent);
        } else if (id == R.id.nav_manage) {
            Intent updateintent = new Intent(this,SynergiaActivity.class);
            startActivity(updateintent);
        } else if (id == R.id.nav_location) {

            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setComponent(ComponentName.unflattenFromString("com.google.android.apps.maps/com.google.android.maps.MapsActivity"));
//            intent.addCategory(android.location.Geocoder);
// replace string with your Google My Map URL
            intent.setData(Uri.parse("https://drive.google.com/open?id=1CMa2_TuP6Xz3aphdn6VsSTZgMj0PXu0w&usp=sharing"));
            startActivity(intent);

        } else if (id == R.id.nav_webpage) {
                WebView browser = (WebView) findViewById(R.id.webView);//i leksi browser ego tin onomazo etsi mporei na einai Andreas
                browser.setWebViewClient(new WebViewClient());//tha fortoso mesa sto programma istoselida
                //browser.setWebChromeClient(new WebChromeClient());//anoigei to google chrome san external efarmogi
                browser.loadUrl("http://www.wbl.com.cy/el/page/home");//mia istoselida gia na fortothei prepei na graftei to programa epikinonias. to kano oso pio aplo mporo gia na grapsei mono tin istoselida. An thelo evaza kai .com
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
