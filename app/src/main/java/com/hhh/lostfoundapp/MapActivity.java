package com.hhh.lostfoundapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

public class MapActivity extends AppCompatActivity {
    MapView mMapView = null;
    BaiduMap mBaiduMap;
    public LocationClient mLocationClient = null;
    MyLocationListener listener = new MyLocationListener();
    boolean isFirstLoc = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SDKInitializer.initialize(this);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);



        mLocationClient = new LocationClient(this);

        mLocationClient.registerLocationListener(listener);
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);


        option.setCoorType("bd09ll");


        int span = 1000;
        option.setScanSpan(span);


        option.setIsNeedAddress(true);


        option.setOpenGps(true);


        option.setLocationNotify(true);


        option.setIsNeedLocationDescribe(true);


        option.setIsNeedLocationPoiList(true);


        option.setIgnoreKillProcess(false);


        option.SetIgnoreCacheException(false);

        option.setEnableSimulateGps(false);


        mLocationClient.setLocOption(option);
    }
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (isFirstLoc) {
                isFirstLoc = false;

                StringBuffer sb = new StringBuffer(256);

                sb.append("time : ");
                sb.append(location.getTime());

                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());

                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());

                sb.append("\nradius : ");
                sb.append(location.getRadius());

                if (location.getLocType() == BDLocation.TypeGpsLocation) {


                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());

                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());

                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());

                    sb.append("\ndirection : ");
                    sb.append(location.getDirection());

                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());

                    sb.append("\ndescribe : ");
                    sb.append("gps");

                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {


                    sb.append("\naddr : ");
                    sb.append(location.getAddrStr());

                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());

                    sb.append("\ndescribe : ");
                    sb.append("gps");

                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {


                    sb.append("\ndescribe : ");
                    sb.append("");

                } else if (location.getLocType() == BDLocation.TypeServerError) {

                    sb.append("\ndescribe : ");
                    sb.append("");

                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                    sb.append("\ndescribe : ");
                    sb.append("");

                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                    sb.append("\ndescribe : ");
                    sb.append("");

                }

                sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());
                List<Poi> list = location.getPoiList();
                if (list != null) {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list) {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
                LatLng lpoint = new LatLng(location.getLatitude(), location.getLongitude());
                System.out.println(location.getLatitude()+","+location.getLongitude());
                BitmapDescriptor bitmapDescriptorFactory = BitmapDescriptorFactory.fromResource(R.drawable.point);
                OverlayOptions options = new MarkerOptions().position(lpoint).icon(bitmapDescriptorFactory);
                mBaiduMap.addOverlay(options);
                MapStatus mapstat = new MapStatus.Builder().target(lpoint).zoom(18).build();
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapstat);
                mBaiduMap.setMapStatus(mapStatusUpdate);
            }
        }
    }
}
