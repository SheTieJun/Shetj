package me.shetj.base.tools.app;


public class ButtonUtil {

	private static long lastClickTime;


	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if ( 0 < timeD && timeD < 2000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

}
