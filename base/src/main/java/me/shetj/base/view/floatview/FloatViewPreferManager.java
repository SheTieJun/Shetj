package me.shetj.base.view.floatview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class FloatViewPreferManager {
	private Context mContext;

	public FloatViewPreferManager(Context context) {
		mContext = context;
	}

	/**
	 * get the shared proferences for getting or setting
	 * 
	 * @return SharedPreferences
	 */
	private SharedPreferences getSharedPrefernces() {
		return mContext.getSharedPreferences("share_float",
				Context.MODE_PRIVATE);
	}

	/**
	 * get the editor for saving the key value
	 * 
	 * @return Editor
	 */
	private Editor getEditor() {
		return getSharedPrefernces().edit();
	}

	public float getFloatX() {
		SharedPreferences swg = getSharedPrefernces();

		return swg.getFloat("float_x", 0f);
	}

	public void setFloatX(float x) {
		Editor editor = getEditor();
		editor.putFloat("float_x", x);
		editor.commit();
	}

	public float getFloatY() {
		SharedPreferences swg = getSharedPrefernces();

		return swg.getFloat("float_y", 300f);
	}

	public void setFloatY(float y) {
		Editor editor = getEditor();
		editor.putFloat("float_y", y);
		editor.commit();
	}

	boolean isDisplayRight() {
		SharedPreferences swg = getSharedPrefernces();

		return swg.getBoolean("PREF_KEY_IS_RIGHT", false);
	}

	public void setDisplayRight(boolean isRight) {
		Editor editor = getEditor();
		editor.putBoolean("PREF_KEY_IS_RIGHT", isRight);
		editor.commit();
	}



	// 是否第一次出现悬浮球
	public boolean getFirstFloatView() {
		SharedPreferences swg = getSharedPrefernces();
		boolean firstFloatView=swg.getBoolean("FIRST_FLOAT_VIEW", true);
		if (firstFloatView) {
			Editor editor = getEditor();
			editor.putBoolean("FIRST_FLOAT_VIEW", false);
			editor.commit();
		}
		return  firstFloatView;
	}
	
	public void deleteFirstFloatView() {
			Editor editor = getEditor();
			editor.remove("FIRST_FLOAT_VIEW");
			editor.commit();
	}

	public void setIsCanMove(boolean isCanMove) {
		Editor editor = getEditor();
		editor.putBoolean("PREF_KEY_IS_MOVE", isCanMove);
		editor.commit();
	}


	public boolean getIsCanMove( ) {
		SharedPreferences swg = getSharedPrefernces();
		return swg.getBoolean("PREF_KEY_IS_MOVE",true);
	}
}
