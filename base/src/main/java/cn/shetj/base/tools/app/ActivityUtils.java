package cn.shetj.base.tools.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

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

    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    //移除fragment
    protected void removeFragment(@NonNull FragmentManager fragmentManager) {
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        }
    }


    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }
    public static void showNoImplementText(Context context){
        Toast.makeText(context,"Add more codes to support!", Toast.LENGTH_SHORT).show();
    }
}
