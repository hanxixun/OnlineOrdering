package com.od.onlineordering;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class Me_register extends Activity implements View.OnClickListener {
    private EditText userPhone_et, passWord_et;
    private Button Message_btn, login_btn;
    public final static String EXTRA_MASSAGE = "com.od.onlineordering.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_register);

        initBomb();
        initView();
        initEvent();
    }

    private void initEvent() {
        login_btn.setOnClickListener(this);
        Message_btn.setOnClickListener(this);
    }

    private void initView() {
        userPhone_et = (EditText) findViewById(R.id.userPhone_et);
        passWord_et = (EditText) findViewById(R.id.passWord_et);
        Message_btn = (Button) findViewById(R.id.Message_btn);
        login_btn = (Button) findViewById(R.id.login_btn);
    }

    private void initBomb() {
        Bmob.initialize(this, "2ba4db9c29a59ac93fccb52ac83fa0b5");
    }

    /**
     * 目标要求：输入手机号，点击获取验证码，用户把验证码填写完毕，点击登录
     * 细节：
     * 1、输入手机号时，判断是不是11位手机号，不是11位，当点击获取
     * 验证码按钮时则提示-->请输入11位有效手机号码，是11位，则进行点击获取验
     * 证码操作，并提示验证码已发送，请尽快使用
     * 2、当进行获取验证码操作后，获取验证码按钮变成灰色，且不可点击，并进行
     * 倒计时操作，倒计时1分钟后可以重新点击，再次发送验证码
     * 3、点击登录按钮时，若手机号和验证码有一个为空，则提示手机号与验证码
     * 不能为空，若验证码已填写，则判断用户填写所验证码与系统发送验证码是否一致，
     * 一致则提示登录成功，错误则提示验证码输入错误
     */
    @Override
    public void onClick(View v) {
        Log.e("MESSAGE:", "1");
        final String userPhone = userPhone_et.getText().toString();
        String passWord = passWord_et.getText().toString();

        switch (v.getId()) {
            case R.id.Message_btn:
                Log.e("MESSAGE:", "2");
                if (userPhone.length() != 11) {
                    Toast.makeText(this, "请输入11位有效手机号码", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("MESSAGE:", "3");
                    //进行获取验证码操作和倒计时1分钟操作
                    BmobSMS.requestSMSCode(userPhone, "短信模板", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e == null) {
                                //发送成功时，让获取验证码按钮不可点击，且为灰色
                                Message_btn.setClickable(false);
                                Message_btn.setBackgroundColor(Color.GRAY);
                                Toast.makeText(Me_register.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();
                                /**
                                 * 倒计时1分钟操作
                                 * 说明：
                                 * new CountDownTimer(60000, 1000),第一个参数为倒计时总时间，第二个参数为倒计时的间隔时间
                                 * 单位都为ms，其中必须要实现onTick()和onFinish()两个方法，onTick()方法为当倒计时在进行中时，
                                 * 所做的操作，它的参数millisUntilFinished为距离倒计时结束时的时间，以此题为例，总倒计时长
                                 * 为60000ms,倒计时的间隔时间为1000ms，然后59000ms、58000ms、57000ms...该方法的参数
                                 * millisUntilFinished就等于这些每秒变化的数，然后除以1000，把单位变成秒，显示在textView
                                 * 或Button上，则实现倒计时的效果，onFinish()方法为倒计时结束时要做的操作，此题可以很好的
                                 * 说明该方法的用法，最后要注意的是当new CountDownTimer(60000, 1000)之后，一定要调用start()
                                 * 方法把该倒计时操作启动起来，不调用start()方法的话，是不会进行倒计时操作的
                                 */
                                new CountDownTimer(60000, 1000) {

                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        Message_btn.setBackgroundResource(R.drawable.button_shape02);
                                        Message_btn.setText(millisUntilFinished / 1000 + "秒");
                                    }

                                    @Override
                                    public void onFinish() {
                                        Message_btn.setClickable(true);
                                        Message_btn.setBackgroundResource(R.drawable.btn3_style);
                                        Message_btn.setText("重新发送");
                                    }
                                }.start();
                                Log.e("MESSAGE:", "4");
                            } else {
                                Toast.makeText(Me_register.this, "验证码发送失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.login_btn:
                Log.e("MESSAGE:", "5");

                if (userPhone.length() == 0 || passWord.length() == 0 || userPhone.length() != 11) {
                    Log.e("MESSAGE:", "6");
                    Toast.makeText(Me_register.this, "手机号或验证码输入不合法", Toast.LENGTH_SHORT).show();
                }

                final UserBean userBeanObj = new UserBean();
                userBeanObj.setUserPhone(userPhone);
                userBeanObj.setPassWord(passWord);
                userBeanObj.setPrice("1000");
                userBeanObj.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        //Toast.makeText(Me_register.this, "插入成功", Toast.LENGTH_SHORT).show();
                    }
                });
                BmobSMS.verifySmsCode(userPhone, passWord, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.e("MESSAGE:", "7");
                            Toast.makeText(Me_register.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Me_register.this, MainActivity.class);
                            intent.putExtra(EXTRA_MASSAGE, userBeanObj.getPrice());
                            startActivity(intent);
                            Me_register.this.finish();
                        } else {
                            Log.e("MESSAGE:", "8");
                            Toast.makeText(Me_register.this, "验证码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }
}
