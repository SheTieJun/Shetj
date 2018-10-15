package me.shetj.bdmap;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

import org.simple.eventbus.EventBus;

import me.shetj.base.base.BaseMessage;
import me.shetj.base.tools.app.Utils;
import timber.log.Timber;

import static me.shetj.bdmap.BDMapLocation.SEND_MAP;
import static me.shetj.bdmap.BDMapLocation.SEND_POSITION;


public class BDLocationListener extends BDAbstractLocationListener {


	@Override
	public void onReceiveLocation(BDLocation location){
		double latitude = location.getLatitude();
		//获取纬度信息
		double longitude = location.getLongitude();
		//获取经度信息
		float radius = location.getRadius();
		//获取定位精度，默认值为0.0f
		String coorType = location.getCoorType();
		int errorCode = location.getLocType();
		String address = location.getAddrStr();
		//获取详细地址信息
		String country = location.getCountry();
		//获取国家
		String province = location.getProvince();
		//获取省份
		String city = location.getCity();
		//获取城市
		String district = location.getDistrict();
		//获取区县
		String street = location.getStreet();
		//获取街道信息
		String locationDescribe = location.getLocationDescribe();
		//获取位置描述信息


		Timber.i("位置信息：~");
		Timber.i("纬度信息：latitude = %s", latitude);
		Timber.i("经度信息：longitude = %s", longitude);
		Timber.i("定位精度：radius = %s", radius);
		Timber.i("坐标类型：coorType = %s", coorType);
		Timber.i("错误类型：errorCode = %s", errorCode);
		Timber.i("地址信息: address = %s", address);
		Timber.i("国家信息：country = %s", country);
		Timber.i("省份信息：province = %s", province);
		Timber.i("城市信息：city = %s", city);
		Timber.i("区县信息：district = %s", district);
		Timber.i("街道信息：street = %s", street);
		Timber.i("描述信息：locationDescribe = %s", locationDescribe);

		BDMapLocation.SendTag sendTag = BDMapLocation.getInstance(Utils.getApp().getApplicationContext()).getSendTag();
		BDMapLocation.getInstance(Utils.getApp().getApplicationContext()).saveNowLocation(location);
		String tag = sendTag == BDMapLocation.SendTag.DEF ? SEND_POSITION :SEND_MAP;

		BaseMessage message = new BaseMessage();
		message.obj = location;
		EventBus.getDefault().post(message, tag);
	}
}