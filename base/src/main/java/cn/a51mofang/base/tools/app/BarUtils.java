package cn.a51mofang.base.tools.app;

import android.content.Context;
import android.content.res.Resources;

/**
 * <pre>
 *     desc  : 栏相关工具类
 * </pre>
 */
public final class BarUtils {


    private BarUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取状态栏高度(px)
     *
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

}