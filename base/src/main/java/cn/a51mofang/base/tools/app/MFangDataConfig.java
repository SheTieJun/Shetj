package cn.a51mofang.base.tools.app;

import cn.a51mofang.base.BuildConfig;

/**
 * Created by weedys on 2017/2/23.
 */
public class MFangDataConfig {
    /**
     * The constant DEBUG.
     */
    public final static boolean DEBUG = BuildConfig.DEBUG;

    /**
     * 存储KEY
     */
    public final static String PRE_DEVICEID="device_unid";

    /**
     * The constant PRE_CUSTOM_USER.
     */
    public final static String PRE_CUSTOM_USER="custom_user";

    /**
     * The constant PRE_CUSTOM_TOKEN.
     */
    public final static String PRE_CUSTOM_TOKEN="http_token";
    /**
     * The constant PRE_CUSTOM_TOKEN_FAILURE_TIME.
     */
    public final static String PRE_CUSTOM_TOKEN_FAILURE_TIME ="token_failure_time";

    /**
     * The constant PRE_REFRESH_TOKEN.
     */
    public final static String PRE_REFRESH_TOKEN = "refresh_token";

    /**
     * The constant PRE_SERVER_PHONE.
     */
    public final static String PRE_SERVER_PHONE="service_phone";


    /**
     * The constant PRE_APP_SECRET.
     */
    public final static String PRE_APP_SECRET="app_secret_key";


    //悬浮球相关
    public static final String PREFERENCE_NAME              = "prefer_floating";
    public static final String PREF_KEY_FLOAT_X				= "float_x";
    public static final String PREF_KEY_FLOAT_Y				= "float_y";
    public static final String PREF_KEY_IS_RIGHT			= "is_right";
    public static final String FIRST_FLOAT_VIEW             = "first_float_view";
    public static final String PREF_KEY_IS_MOVE			    = "is_move";


    /**
     * The constant REQUEST_CODE_PAY.
     */
    public static final int REQUEST_CODE_PAY = 0x001;//发起支付请求
    /**
     * The constant RESULT_CODE_PAY_OK.
     */
    public static final int RESULT_CODE_PAY_OK = 0x002;//成功
    /**
     * The constant RESULT_CODE_PAY_FAIL.
     */
    public static final int RESULT_CODE_PAY_FAIL = 0x003;//失败
    /**
     * The constant RESULT_CODE_PAY_CANCEL.
     */
    public static final int RESULT_CODE_PAY_CANCEL = 0x004;//取消

    /**
     *
     */
    public static final int REQUEST_COUPON = 0x005;

    /**
     * The constant MODE_MBPAY.
     */
    public static int MODE_MBPAY = 2;
    /**
     * The constant MODE_ALIPAY.
     */
    public static int MODE_ALIPAY = 3;
    /**
     * The constant MODE_WXPAY.
     */
    public static int MODE_WXPAY = 4;
    /**
     * The constant MODE_WXPAY.
     */
    public static int MODE_FLPAY = 8;


    public static String LAYOUT_LINEARLAYOUT = "LinearLayout";
    public static String LAYOUT_FRAMELAYOUT = "FrameLayout";
    public static String LAYOUT_RELATIVELAYOUT = "RelativeLayout";

}
