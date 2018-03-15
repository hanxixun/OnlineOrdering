package com.od.onlineordering;

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
 * HomeFragment
 *
 * @author: hanxixun
 * @time: 2016/8/20 19:07
 */
public class HomeFragment extends Fragment {

    public WebView WebViewTab01;
    private WebSettings webSettings;
    private String url = "file:///android_asset/food.html";
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
        WebViewTab01.goBack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab01, null);
        WebViewTab01 = (WebView) view.findViewById(R.id.webviewTab01);
        webSettings = WebViewTab01.getSettings();
//        image();
        WebViewTab01.setVerticalScrollbarOverlay(true);
        WebViewFunction();
        //如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点。
        WebViewTab01.requestFocusFromTouch();
        WebViewTab01.loadUrl(url);
        WebViewTab01.setWebViewClient(new Tab01WebViewClient());
        WebViewTab01.setWebChromeClient(new WebChromeClient());
        WebViewTab01.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && WebViewTab01.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    //           getActivity().overridePendingTransition(R.anim.out_to_left, R.anim.in_from_fight);
                    return true;
                }
                return false;
            }

        });
        return view;
    }

    private class Tab01WebViewClient extends WebViewClient {
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
