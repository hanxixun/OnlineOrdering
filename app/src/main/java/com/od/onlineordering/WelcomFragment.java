package com.od.onlineordering;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ${hanxixun} on 2017/4/30.
 */

public class WelcomFragment extends Fragment {

    public WebView WebViewTabWelcom;
    private WebSettings webSettings;
    private String url = "file:///android_asset/begin1.html";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1: {
                    webViewGoBack();
                }
                break;
            }
        }
    };

    private void webViewGoBack() {
        WebViewTabWelcom.goBack();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_welcom, null);
        WebViewTabWelcom = (WebView) view.findViewById(R.id.webviewTabWelcom);
        webSettings = WebViewTabWelcom.getSettings();
//        image();
        WebViewFunction();
        //如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点。
        WebViewTabWelcom.requestFocusFromTouch();
        WebViewTabWelcom.loadUrl(url);
        WebViewTabWelcom.setWebViewClient(new TabWelcomWebViewClient());
        WebViewTabWelcom.setWebChromeClient(new WebChromeClient());
        WebViewTabWelcom.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && WebViewTabWelcom.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    //           getActivity().overridePendingTransition(R.anim.out_to_left, R.anim.in_from_fight);
                    return true;
                }
                return false;
            }

        });

        return view;
    }

    private class TabWelcomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

//        @Override
//        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            super.onReceivedError(view, errorCode, description, failingUrl);
//            view.loadUrl("file:///android_asset/WebBreak.html");
//            //   view.loadUrl("www.baidu.com");
//        }
    }

    public void WebViewFunction() {
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
