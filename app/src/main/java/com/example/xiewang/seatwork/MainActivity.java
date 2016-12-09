package com.example.xiewang.seatwork;

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
import android.view.KeyEvent;
import android.support.design.widget.TextInputLayout;
import android.widget.ImageButton;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.app.TabActivity;
import android.content.Intent;
import android.net.wifi.*;
import android.widget.Toast;
import android.content.Context;

public class MainActivity  extends TabActivity {
    private WebView webView;
    private WifiManager wifiManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);

        TabHost tah = getTabHost();
        TabHost.TabSpec spec;

//        LayoutInflater.from(this).inflate(R.layout.activity_main, tah.getTabContentView(), true);

//        tah.addTab(tah.newTabSpec("contacts").setIndicator("通讯录").setContent(R.id.contacts));
        spec=getTabHost().newTabSpec("contacts");
        Intent contacts = new Intent(this,Contacts.class);
        spec.setContent(contacts);
        spec.setIndicator("contacts");
        getTabHost().addTab(spec);

        tah.addTab(tah.newTabSpec("browser").setIndicator("webView").setContent(R.id.browser));


        ImageButton btn =  (ImageButton) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout urlWrapper = (TextInputLayout) findViewById(R.id.urlWrapper);
                String url = urlWrapper.getEditText().getText().toString();
                if(getWifi() == 3){
                    webView(url);
                } else {
                    Toast toast=Toast.makeText(getApplicationContext(), "请检查wifi的状态", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        webView("");



    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();
                return true;
            }
            else
            {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
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



    public int getWifi(){
        int state = wifiManager.getWifiState();
        return state;
    }

    public void webView(String url){
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
