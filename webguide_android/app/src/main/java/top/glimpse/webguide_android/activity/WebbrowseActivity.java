package top.glimpse.webguide_android.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import top.glimpse.webguide_android.R;


public class WebbrowseActivity extends Activity {


    private static final String TAG = "WebbrowseActivity";
    //private Button button;
    private WebView webView;




    public void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        //This will not show title bar 
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_webbrowse);

        ImageView back = (ImageView) findViewById(R.id.back);
        ImageView home = (ImageView) findViewById(R.id.home);
        back.setOnClickListener(new MyOnClickListener());
        home.setOnClickListener(new MyOnClickListener());



        Intent  intent = getIntent();
        String url = intent.getStringExtra("url");


        //Get webview
        webView = (WebView) findViewById(R.id.webView);
        if(haveNetworkConnection()){
            startWebView(url);
        } else {
            webView.loadUrl("file:///android_asset/error.html");
        }
    }



    private void startWebView(String url) {
         
        //Create new webview Client to show progress dialog
        //When opening a url or click on link


        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(WebbrowseActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "HELLO,关了吗？");
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
             
        });
          
         // Javascript inabled on webview 
        webView.getSettings().setJavaScriptEnabled(true);



        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        //Additional Webview Properties
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setDatabaseEnabled(true);
		webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
		webView.getSettings().setAppCacheEnabled(true);
	    webView.getSettings().setLayoutAlgorithm(webView.getSettings().getLayoutAlgorithm().NORMAL);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(false);
		webView.setSoundEffectsEnabled(true);
		webView.setHorizontalFadingEdgeEnabled(false);
		webView.setKeepScreenOn(true);
		webView.setScrollbarFadingEnabled(true);
		webView.setVerticalFadingEdgeEnabled(false);
		*/

        webView.loadUrl(url);


    }
    
    
     
    // Open previous opened link from history on webview when back button pressed
     
    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private class MyOnClickListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back:
                    onBackPressed();
                    break;
                case R.id.home:
                    Intent intent = new Intent(WebbrowseActivity.this, WebguideActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:

                    break;
            }
        }
    }
}
