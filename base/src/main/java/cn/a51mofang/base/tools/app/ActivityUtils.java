package cn.a51mofang.base.tools.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by admin on 2017/9/13.
 */

public class ActivityUtils {
    public static void openActivity(Activity activity,String scheme){
        String url = "mofang://"+scheme;
        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(in);
    }

    public static void openActivityByPackageName(Activity activity,String ackageName){
        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(ackageName);
        activity.startActivity(intent);
    }
}
