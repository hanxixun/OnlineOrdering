package com.od.onlineordering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Me_Login extends Activity implements View.OnClickListener{
    private TextView mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 第一种方法
        setContentView(R.layout.me_layout_login);

        initView();
        initEvent();
    }

    private void initEvent() {
        mRegister.setOnClickListener(this);
    }

    private void initView() {
        mRegister = (TextView) findViewById(R.id.me_login_register);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Me_Login.this,Me_register.class);
        this.startActivity(intent);
        this.finish();
    }
}
