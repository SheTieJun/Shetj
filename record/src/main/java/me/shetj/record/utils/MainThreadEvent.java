package me.shetj.record.utils;


public class MainThreadEvent<T> extends BaseEvent<T> {
    public static final int TYPE_SUCCESS_PAY_LECTURE = 0x110;// 支付成功课程
    public static final int TYPE_SUCCESS_PAY_CHANNEL = 0x111;// 支付成功专栏
    public static final int TYPE_SUCCESS_PAY_VIP = 0x112;// 支付成功vip
    public static final int TYPE_SUCCESS_PAY_REDPACKET = 0x113;// 支付成功红包
    public static final int TYPE_SUCCESS_PAY_CREATE_LIVEROOM = 0x131;//创建直播间
    public static final int TYPE_FAIL_PAY_LECTURE = 0x114;// 支付失败课程
    public static final int TYPE_FAIL_PAY_CHANNEL = 0x115;// 支付失败专栏
    public static final int TYPE_FAIL_PAY_VIP = 0x116;// 支付失败vip
    public static final int TYPE_SUCCESS_PAY_CHANNEL_FREE = 0x117;// 限时免费/优惠券全额支付专栏
    public static final int TYPE_SUCCESS_PAY_LECTURE_FREE = 0x118;// 限时免费/优惠券全额支付课程
    public static final int TYPE_SUCCESS_PAY_VIP_FREE = 0x119;// 穷逼vip
    public static final int DOWNLOAD_IMAGE_SUCCESS = 0x123;// 下载成功
    public static final int DOWNLOAD_IMAGE_FAILED = 0x124;// 下载失败
    public static final int UPDATE_PLAY_LIST_DATA = 0x135;//更新播放列表数据
    public static final int SOCKET_CONNECT = 0x136;//连接socket
    public static final int SOCKET_DISCONNECT = 0x137;//断开socket
    public static final int PLAY_NEW_AUDIO = 0x138;//播放新语音
    public static final int PLAY_ASSIGN_LECTURE_UPDATE_UI = 0x139;//上一首、下一首、播放完毕切换下一首、点击切换歌曲
    public static final int AUDIO_SPEED_RATE_CHANGED = 0x140;//全局音频倍速倍率更改通知
    public static final int AUDIO_SPEED_RATE_SUPPORT = 0x141;//全局音频倍速倍率更改通知
    public static final int REQUEST_APP_CONFIG_SUCCESS = 0x142;//请求APP配置接口成功
    public static final int REQUEST_APP_CONFIG_FAILED = 0x143;//请求APP配置接口失败
    public static final int REQUEST_APP_VERSION_SUCCESS = 0x144;//请求APP升级接口成功
    public static final int REQUEST_APP_VERSION_FAILED = 0x145;//请求APP升级接口失败

    public static final int NETWORK_CONNECTED = 0x146;//网络已连接，全局监听
    public static final int NETWORK_DISCONNECTED = 0x147;//网络已断开，全局监听

    public static final int NETWORK_WIFI_DISCONNECTED = 0x200;//wifi网络断了，在视频播放器那里用到
    public static final int NETWORK_WIFI_CONNECTED = 0x201;//wifi网络连上了，在视频播放器那里用到

    public static final int SEARCH_HISTORY_CLICK = 0x300;//历史搜索和关键字搜索
    public static final int SEARCH_HISTORY_SAVE = 0x301;//保存历史搜索记录
    public static final int SEARCH_HOTKEY_CLICK = 0x302;//关键字搜索

    public static final int STAT_ONLINE = 0x400;//全局在线统计
    public static final int STAT_ONLINE_PAUSE = 0x401;//全局在线统计

    public static final int RECORD_REFRESH_MY = 0x501;//刷新我的录音界面
    public static final int RECORD_REFRESH_DEL = 0x502;//刷新我的录音界面
    public static final int RECORD_REFRESH_RECORD = 0x504;//已经存在刷新我的录音
    public static final int RECORD_POST_URL = 0x505;//通知上传url成功
    public static final int RECORD_REFRESH_LIVEROOM_ID = 0x605;//通知上传url成功

    public static final int TIP_MESSAGE_INFO = 0x601; //INFO展示tip
    public static final int TIP_MESSAGE_WARNING = 0x602; //WARNING展示tip
    public static final int TIP_MESSAGE_ERROR = 0x603; //WARNING展示tip

    public static final int CACHE_VIDEO_CAN_DOWNLOAD = 0x701;//下载数据服务链接成功


    public static final int PLAY_NEXT_RECORD_LECTURE = 0x801;//下载数据服务链接成功

    public static final int PLAT_OPEN_LECTURE = 0x901;


    public MainThreadEvent() {
        super();
    }

    public MainThreadEvent(int type, T content) {
        super(type, content);
    }
}
