package com.shetj.diyalbume.map;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shetj.diyalbume.R;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import me.shetj.aspect.permission.MPermission;
import me.shetj.base.base.BaseActivity;
import me.shetj.base.base.BaseMessage;
import me.shetj.base.tools.json.GsonKit;
import me.shetj.bdmap.BDMapLocation;
import me.shetj.bdmap.MapActivity;

import static me.shetj.bdmap.BDMapLocation.SEND_POSITION;

/**
 * 地图相关
 *
 * @author shetj
 */
public class BDMapActivity extends BaseActivity implements View.OnClickListener {

	private TextView mTvPosition;
	/**
	 * 地图
	 */
	private Button mBtnMap;
	/**
	 * 我的位置
	 */
	private Button mBtnLocal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bdmap);
		initView();
		initData();
	}

	@Override
	protected void initView() {

		mTvPosition = (TextView) findViewById(R.id.tv_position);
		mBtnMap = (Button) findViewById(R.id.btn_map);
		mBtnMap.setOnClickListener(this);
		mBtnLocal = (Button) findViewById(R.id.btn_local);
		mBtnLocal.setOnClickListener(this);
	}

	@Override
	protected void initData() {

	}


	@MPermission(value ={(Manifest.permission.ACCESS_FINE_LOCATION)})
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			default:
				break;
			case R.id.btn_map:
				startActivity(new Intent(this, MapActivity.class));
				break;
			case R.id.btn_local:
				BDMapLocation.getInstance(this).setOption(true);
				BDMapLocation.getInstance(this).start(BDMapLocation.SendTag.DEF);
				break;
		}
	}


	@Subscriber(tag =  SEND_POSITION, mode = ThreadMode.ASYNC)
	public void location(BaseMessage location){
		mTvPosition.setText(GsonKit.objectToJson(location.obj));
	}
}
