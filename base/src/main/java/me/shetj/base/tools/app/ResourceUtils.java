package me.shetj.base.tools.app;

import android.content.Context;

public class ResourceUtils {

    public static int getIdByName(Context context, String className, String resName) {
        context = context.getApplicationContext();
        String packageName = context.getPackageName();
        int indentify = context.getResources().getIdentifier(resName, className, packageName);
        return indentify;
    }
}
