package com.od.onlineordering;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragment;

    private LinearLayout mTabHome;
    private LinearLayout mTabNearby;
    private LinearLayout mTabList;
    private LinearLayout mTabMe;

    private ImageButton mHomeImg;
    private ImageButton mNearbyImg;
    private ImageButton mListImg;
    private ImageButton mMeImgs;
    public  static String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
        setSelect(0);

        Intent intent = getIntent();
        message = intent.getStringExtra(Me_register.EXTRA_MASSAGE);

    }

    private void initEvent() {
        mTabHome.setOnClickListener(this);
        mTabNearby.setOnClickListener(this);
        mTabList.setOnClickListener(this);
        mTabMe.setOnClickListener(this);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        //tabs
        mTabHome = (LinearLayout) findViewById(R.id.id_tab_home);
        mTabNearby = (LinearLayout) findViewById(R.id.id_tab_nearby);
        mTabList = (LinearLayout) findViewById(R.id.id_tab_list);
        mTabMe = (LinearLayout) findViewById(R.id.id_tab_me);

        //imageButton
        mHomeImg = (ImageButton) findViewById(R.id.id_tab_home_img);
        mNearbyImg = (ImageButton) findViewById(R.id.id_tab_nearby_img);
        mListImg = (ImageButton) findViewById(R.id.id_tab_list_img);
        mMeImgs = (ImageButton) findViewById(R.id.id_tab_me_img);

        //四个数据源Fragment初始化
        mFragment = new ArrayList<Fragment>();
        Fragment mTab01 = new HomeFragment();
        Fragment mTab02 = new NearbyFragment();
        Fragment mTab03 = new ListFragment();
        Fragment mTab04 = new MeFragment();
        mFragment.add(mTab01);
        mFragment.add(mTab02);
        mFragment.add(mTab03);
        mFragment.add(mTab04);

        //Adapter初始化
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };
        //为ViewPager设置适配器
        mViewPager.setAdapter(mAdapter);
        //实现ViewPager的联动效果，监听下ViewPager的变化
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = mViewPager.getCurrentItem();
                setTab(currentItem);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 调用setTab方法
     * 获取当前的Tab页面，用来切换内容
     *
     * @param i
     */
    private void setSelect(int i) {
        setTab(i);
        mViewPager.setCurrentItem(i);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.id_tab_home:
                setSelect(0);
                break;
            case R.id.id_tab_nearby:
                setSelect(1);
                break;
            case R.id.id_tab_list:
                setSelect(2);
                break;
            case R.id.id_tab_me:
                setSelect(3);
                break;
        }
    }

    /**
     * 设置图片亮色
     * 设置内容区域
     *
     * @param i
     */
    private void setTab(int i) {
        resetImg();
        switch (i) {
            case 0:
                mHomeImg.setImageResource(R.mipmap.tab_home_fill);
                break;
            case 1:
                mNearbyImg.setImageResource(R.mipmap.tab_nearby_fill);
                break;
            case 2:
                mListImg.setImageResource(R.mipmap.tab_list_fill);
                break;
            case 3:
                mMeImgs.setImageResource(R.mipmap.tab_me_fill);
                break;
        }
    }

    /**
     * 将所有图片切换为暗色
     */
    private void resetImg() {
        mHomeImg.setImageResource(R.mipmap.tab_home_normal);
        mNearbyImg.setImageResource(R.mipmap.tab_nearby_normal);
        mListImg.setImageResource(R.mipmap.tab_list_normal);
        mMeImgs.setImageResource(R.mipmap.tab_me_normal);
    }

    /**
     * 按返回键调用，并调用显示AlertDialog
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果是返回键,直接返回到桌面
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            moveTaskToBack(false);                  //屏蔽返回行为
            showExitGameAlert();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showExitGameAlert() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        //将窗口背景设为透明
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable());
        //  主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.dialog);
        // 为确认按钮添加事件,执行退出应用操作
        ImageButton ok = (ImageButton) window.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();   // 退出应用...
            }
        });
        // 关闭alert对话框架
        ImageButton cancel = (ImageButton) window.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
            }
        });
    }


}
