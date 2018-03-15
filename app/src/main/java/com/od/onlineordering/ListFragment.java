package com.od.onlineordering;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

/**
 * ListFragment
 *
 * @author: hanxixun
 * @time: 2016/8/20 19:07
 */
public class ListFragment extends Fragment {
    public static String price;
    public WebView WebViewTab03;
    private WebSettings webSettings;
    private String url = "file:///android_asset/order.html";
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
        WebViewTab03.goBack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab03, null);
        WebViewTab03 = (WebView) view.findViewById(R.id.webviewTab03);
        webSettings = WebViewTab03.getSettings();
//        image();
        WebViewTab03.setVerticalScrollbarOverlay(false);
        WebViewFunction();
        //如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点。
        WebViewTab03.requestFocusFromTouch();
        WebViewTab03.loadUrl(url);
        //在js中调用本地java方法
        WebViewTab03.addJavascriptInterface(new JsInterface(getActivity()), "AndroidWebView");
        WebViewTab03.setWebViewClient(new ListFragment.Tab03WebViewClient());
        WebViewTab03.setWebChromeClient(new WebChromeClient());
        WebViewTab03.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && WebViewTab03.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    //           getActivity().overridePendingTransition(R.anim.out_to_left, R.anim.in_from_fight);
                    return true;
                }
                return false;
            }

        });

        return view;
    }


    private class Tab03WebViewClient extends WebViewClient {
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

    private class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs(String msg) {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            price=msg;
        }
    }

//    //在java中调用js代码
//    public void sendInfoToJs(View view) {
//        String msg;
//        //调用js中的函数：showInfoFromJava(msg)
//        WebViewTab03.loadUrl("javascript:showInfoFromJava('" + msg + "')");
//    }
}
