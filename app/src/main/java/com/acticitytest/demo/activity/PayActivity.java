package com.acticitytest.demo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.acticitytest.demo.R;
import com.acticitytest.demo.utils.NetworkUtils;
import com.acticitytest.demo.view.MyWebView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PayActivity extends AppCompatActivity {

    @BindView(R.id.pay_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.pay_webView)
    MyWebView mWebView;

    private String pay;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initMyWebView();
        initSwipeRefreshLayout();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("JavascriptInterface")
    private void initMyWebView(){

        mWebView.addJavascriptInterface(this, "app");
        mWebView.setVerticalFadingEdgeEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);

        mWebView.setOnCustomScrollChanged(new MyWebView.IWebViewScroll() {

            @Override
            public void onTop() {

                mSwipeRefreshLayout.setEnabled(true);

            }

            @Override
            public void notOnTop() {

                mSwipeRefreshLayout.setEnabled(false);

            }
        });

        mWebView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    if(i == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){

                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });

        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                if (url.contains("alipays://platformapi")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity( intent );
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest
                    request, WebResourceError error){

                super.onReceivedError(view, request, error);
                mWebView.loadUrl("file://android_assert/error.html");
                mSwipeRefreshLayout.setRefreshing(false);
                Log.e("pay", error.toString());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){

                mSwipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url){

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        pay = getIntent().getStringExtra("payUrl");
        mWebView.loadUrl(pay);
    }

    private void initSwipeRefreshLayout(){

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if(NetworkUtils.isNetworkAvailable(PayActivity.this)){
                    mWebView.reload();
                }else{
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(PayActivity.this, "网络不可用",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
