package me.shetj.bdmap;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

/**
 * <b>@packageName：</b> com.ebu.baidu<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/2/27<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class BDMapLocation {


	public enum SendTag {
		DEF,MAP
	}

	public final static String SEND_POSITION ="send_position";

	public final static String	SEND_MAP = "send_map";

	private static BDMapLocation ourInstance = null;

	private SendTag sendTag = SendTag.DEF;
	private BDLocation location = null;
	public static BDMapLocation getInstance(Context context) {
		if (ourInstance == null){
			synchronized (BDMapLocation.class){
				ourInstance = new BDMapLocation(context);
			}
		}
		return ourInstance;
	}

	private BDMapLocation(Context context) {
		init(context);
	}

	private LocationClient mLocationClient;


	/**
	 * 记录当前位置
	 * @param location
	 */
	public void saveNowLocation(BDLocation location) {
		this.location = location;
	}

	/**
	 * 获取当前位置
	 * @return
	 */
	public BDLocation getNowLocation(){
		if (null == location){
			return new BDLocation();
		}
		return location;
	}

	private void init(Context context){
		mLocationClient = new LocationClient(context.getApplicationContext());
	}

	public void setLocationListener(BDAbstractLocationListener locationListener){
		if (null != mLocationClient) {
			mLocationClient.registerLocationListener(locationListener);
		}
	}


	public void setOption(boolean isOnly){
		LocationClientOption option = new LocationClientOption();

		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；

		option.setCoorType("bd09ll");
//可选，设置返回经纬度坐标类型，默认gcj02
//gcj02：国测局坐标；
//bd09ll：百度经纬度坐标；
//bd09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

		option.setScanSpan(isOnly? 0 :10000);
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效

		option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

		option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

		option.setIgnoreKillProcess(false);
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

		option.SetIgnoreCacheException(false);
//可选，设置是否收集Crash信息，默认收集，即参数为false

		option.setWifiCacheTimeOut(5*60*1000);
//可选，7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

		option.setEnableSimulateGps(false);
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false


		option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true


		option.setIsNeedLocationDescribe(true);
//可选，是否需要位置描述信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的位置信息，此处必须为true

		option.setIsNeedLocationPoiList(true);
//可选，是否需要周边POI信息，默认为不需要，即参数为false
//如果开发者需要获得周边POI信息，此处必须为true


		mLocationClient.setLocOption(option);

//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

	}



	public void start(SendTag tag){
		if (mLocationClient != null ) {
			sendTag = tag;
			mLocationClient.start();
		}
	}

	public void stop(){
		sendTag = SendTag.DEF;
		if (mLocationClient !=null && mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
	}


	public SendTag getSendTag() {
		return sendTag;
	}

	public LatLng getLatLng(double lat, double lng){
		return new LatLng(lat,lng);
	}

	/**
	 * 单位是米
	 * @param latLng1
	 * @param latLng2
	 * @return
	 */
	public String getDistance(LatLng latLng1, LatLng latLng2){
		if (latLng1 == null || latLng2 == null){
			return "距离未知";
		}
		double distance = DistanceUtil.getDistance(latLng1, latLng2);
		if (distance < 0){
			return "小于 1 m";
		}else if (distance <= 100){
			return "100 m 以内";
		}else if (distance <= 1000 ){
			return "1000 m 以内";
		}else if (distance > 1000){
			return ((int)distance/1000+1)+" km 以内";
		}
		return "距离未知";
	}
}
