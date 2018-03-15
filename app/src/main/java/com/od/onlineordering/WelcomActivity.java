package com.od.onlineordering;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ${hanxixun} on 2017/4/30.
 */

public class WelcomActivity extends FragmentActivity {
    private WebView webView_Welcom;
    private WebSettings webSettings;
    private String url = "file:///android_asset/begin.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_welcom);
        init();

        //利用线程延迟时间跳转主界面
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(WelcomActivity.this,Me_Login.class);
                WelcomActivity.this.startActivity(intent);
                WelcomActivity.this.finish();
            }
        }, 4500);
    }

    private void init() {
        webView_Welcom = (WebView) findViewById(R.id.webviewTabWelcom);
        webSettings = webView_Welcom.getSettings();
        WebViewFunction();
        //WebView加载web资源
        webView_Welcom.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView_Welcom.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void WebViewFunction() {
        //支持js
        webSettings.setJavaScriptEnabled(true);
        //将图片调整到适合webview的大小和设置关键点
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //适应屏幕，内容将自动缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //加载图片
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //多窗口
        webSettings.supportMultipleWindows();
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 允许访问文件
        webSettings.setAllowFileAccess(true);
        //设置 缓存模式
        webSettings.setCacheMode(webSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
    }
}

