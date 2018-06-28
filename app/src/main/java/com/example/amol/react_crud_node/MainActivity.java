package com.example.amol.react_crud_node;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.WebViewClient;
import android.view.KeyEvent;


public class MainActivity extends AppCompatActivity {
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        if (!isNetworkAvailable()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("AMOL-Internet Connection Required").setCancelable(true).setPositiveButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog,
                                int id) {
                            // exit from app
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("SUCCESS");
//            AlertDialog alert = builder.create();
//            alert.show();
            myWebView = findViewById(R.id.myWebView);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setWebViewClient(new MyWebViewClient());  //useful for opening links in the webView instead of Browser
//            myWebView.getSettings().setAppCacheEnabled(true);
            myWebView.getSettings().setDomStorageEnabled(true);  // for web related storage such cookies, sessions etc.. and Setting the DOM Storage value to false will prevents local storage from being used.
            myWebView.loadUrl("https://react-crud-node.herokuapp.com");
        }
    }

    private class MyWebViewClient extends WebViewClient{
        //we can override this method for opening any external link in the WebView instead of Browser
    }

    // Private class isNetworkAvailable
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    // For Back & Forward button of App -- imported keyEvent package
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

}
