package me.shetj.share;

import android.util.Log;

/**
 * Created by shaohui on 2016/12/8.
 */

public class ShareLogger {

    private static final String TAG = "ShareLogger";

    public static void i(String info) {
        if (ShareManager.CONFIG.isDebug()) {
            Log.i(TAG, info);
        }
    }

    public static void e(String error) {
        if (ShareManager.CONFIG.isDebug()) {
            Log.e(TAG, error);
        }
    }

    public static class INFO {
        // for share
        public static final String HANDLE_DATA_NULL = "Handle the result, but the data is null, please check you app id";
        public static final String UNKNOWN_ERROR = "Unknown error";

        // for shareActivity
        public static final String ACTIVITY_CREATE = "ShareActivity onCreate";
        public static final String ACTIVITY_RESUME = "ShareActivity onResume";
        public static final String ACTIVITY_RESULT = "ShareActivity onActivityResult";
        public static final String ACTIVITY_NEW_INTENT = "ShareActivity onNewIntent";

    }

}
