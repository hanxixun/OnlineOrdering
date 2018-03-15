package com.od.onlineordering;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

/**
 * NearByFragment
 *
 * @author: hanxixun
 * @time: 2016/8/20 19:07
 */
public class NearbyFragment extends Fragment {

    private MapView mMapView;
    private BaiduMap mBaiduMap;


    //定位的客户端
     private LocationClient mLocationClient;
    //定位的监听器
    private MyLocationListener mMyLocationListener;
    //当前定位的模式
    //private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    //是否是第一次定位
    private boolean isFirstIn = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.tab02, null);

        //初始化定位

        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setTrafficEnabled(true);

        // 开启定位图层
        //mBaiduMap.setMyLocationEnabled(true);


       initLocation();
//        mBaiduMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
//        mLocationClient.start();//开启定位


        /**
         * 定位功能
         * LocationClient进行定位的一些设置
         * BDLocationListener
         */
        return view;
    }

    private void initLocation() {
        mLocationClient = new LocationClient(getActivity());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        //设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        //设置新坐标
        option.setCoorType("bd0911");
        option.setIsNeedAddress(true);
        //打开GPS
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        //开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
    }

    @Override
    public void onStop() {
        //关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        super.onStop();
    }


    /**
     * 实现回调监听
     */
    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //map view 销毁后不再处理新接受的位置
            if (bdLocation == null || mMapView == null)
                return;

            //构造定位数据
            MyLocationData locdata = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            //设置定位数据
            mBaiduMap.setMyLocationData(locdata);
            //MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,arg1,arg2);
            if (isFirstIn) {
                //设置经度和纬度
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                isFirstIn = false;
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
}
