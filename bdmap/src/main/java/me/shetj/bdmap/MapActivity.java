package me.shetj.bdmap;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.shetj.base.base.BaseActivity;
import me.shetj.base.tools.app.ArmsUtils;

import static me.shetj.bdmap.BDMapLocation.SEND_MAP;

/**
 * @author Administrator
 */
public class MapActivity extends BaseActivity implements View.OnClickListener, SensorEventListener {

	private BaiduMap mBaiduMap;
	private BitmapDescriptor mCurrentMarker = null;
	private MyLocationConfiguration.LocationMode mCurrentMode;
	private MyLocationData locData;
	private MapView mMapView;
	/**
	 * 我的位置
	 */
	private ImageView mBtnMy;
	private SensorManager mSensorManager;
	private double lastX;
	private int mCurrentDirection;
	private float mCurrentAccracy;
	private double mCurrentLat;
	private double mCurrentLon;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		ArmsUtils.statuInScreen(this,false);
		BDMapLocation.getInstance(getApplicationContext()).stop();
	}


	@Override
	public void onPause() {
		mMapView.onPause();
		super.onPause();
	}


	@Override
	protected void onStop() {
		//取消注册传感器监听
		mSensorManager.unregisterListener(this);
		super.onStop();
	}

	@Override
	public void onResume() {
		super.onResume();
		//为系统的方向传感器注册监听器
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
						SensorManager.SENSOR_DELAY_UI);
	}



	private void setting() {
		mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
			@Override
			public void onMapLoaded() {
				startShowLocation();
			}
		});

		mBaiduMap.setOnMapDoubleClickListener(new BaiduMap.OnMapDoubleClickListener() {
			@Override
			public void onMapDoubleClick(LatLng latLng) {

			}
		});

		mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				return false;
			}
		});

		mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
			@Override
			public void onMarkerDrag(Marker marker) {

			}

			@Override
			public void onMarkerDragEnd(Marker marker) {

			}

			@Override
			public void onMarkerDragStart(Marker marker) {

			}
		});

		mBaiduMap.setOnMyLocationClickListener(new BaiduMap.OnMyLocationClickListener() {
			/**
			 * 地图定位图标点击事件监听函数
			 */
			@Override
			public boolean onMyLocationClick() {
				return false;
			}
		});

	}

	private void startShowLocation() {
		if (mBaiduMap != null) {
			// 开启定位图层
			mBaiduMap.setMyLocationEnabled(true);
//		mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;//定位跟随态
			//默认为 LocationMode.NORMAL 普通态
			mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
//		mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;  //定位罗盘态

// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
			MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
			mBaiduMap.setMyLocationConfiguration(config);
			BDMapLocation.getInstance(getApplicationContext()).setOption(true);
			BDMapLocation.getInstance(getApplicationContext()).start(BDMapLocation.SendTag.MAP);
		}
	}


	public void showMap() {
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
	}


	@Override
	public void initView() {
		mMapView = findViewById(R.id.mapView);
		mBtnMy = findViewById(R.id.btn_my);
		mBtnMy.setOnClickListener(this);
		mBaiduMap = mMapView.getMap();
//		UiSettings settings=mBaiduMap.getUiSettings();
//		settings.setAllGesturesEnabled(false); //关闭一切手势操作
//		settings.setOverlookingGesturesEnabled(false);//屏蔽双指下拉时变成3D地图
//		settings.setZoomGesturesEnabled(false);//获取是否允许缩放手势返回:是否允许缩放手势
//		mBaiduMap.getUiSettings().setAllGesturesEnabled(false);
//		mMapView.showZoomControls(false);
//		mMapView.showScaleControl(false);
		showMap();
		setting();
	}

	@Override
	public boolean useEventBus() {
		return true;
	}

	@Override
	protected void initData() {
		//获取传感器管理服务
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.btn_my) {
			ArmsUtils.makeText( "正在定位...");
			mBaiduMap.setMyLocationEnabled(true);
			BDMapLocation.getInstance(getApplicationContext()).start(BDMapLocation.SendTag.MAP);
		}
	}
	private int i = 0;
	@Subscriber(tag = SEND_MAP, mode = ThreadMode.POST)
	@Override
	public void updateView(Message message) {
		Disposable subscribe = Flowable.just(message)
						.subscribeOn(AndroidSchedulers.mainThread())
						.compose(bindToLifecycle())
						.subscribe(s -> {
							BDLocation location = (BDLocation) s.obj;
							if (location == null || mBaiduMap == null) {
								return;
							}
							mCurrentLat = location.getLatitude();
							mCurrentLon = location.getLongitude();
							mCurrentAccracy = location.getRadius();
							locData = new MyLocationData.Builder()
											.accuracy(location.getRadius())
											.direction(mCurrentDirection).latitude(location.getLatitude())
											.longitude(location.getLongitude()).build();
							mBaiduMap.setMyLocationData(locData);
							LatLng ll = new LatLng(location.getLatitude(),
											location.getLongitude());
							MapStatus.Builder builder = new MapStatus.Builder();
							builder.target(ll).zoom(13.0f);
							mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
							BDMapLocation.getInstance(getApplicationContext()).stop();

						});
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		double x = sensorEvent.values[SensorManager.DATA_X];
		if (Math.abs(x - lastX) > 1.0) {
			mCurrentDirection = (int) x;
			locData = new MyLocationData.Builder()
							.accuracy(mCurrentAccracy)
							// 此处设置开发者获取到的方向信息，顺时针0-360
							.direction(mCurrentDirection)
							.latitude(mCurrentLat)
							.longitude(mCurrentLon).build();
			mBaiduMap.setMyLocationData(locData);
		}
		lastX = x;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i) {

	}
}
