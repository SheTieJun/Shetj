package cn.a51mofang.base.http.api;


/**
 * Created by admin
 * on 2017/9/28.
 */

public interface ShetjApi {
    boolean isTest = false;

    String MOFANG_HOME = "http://mf.51mofang.cn";//魔方主页


    String HTTP_SMS   = isTest? "http://172.35.2.147:5005/" : "http://mf-1-message.51mofang.cn/" ;
    String HTTP_USER  = isTest? "http://172.35.2.147:5000/" : "http://mf-1-user.51mofang.cn/" ;
    String HTTP_ORDER = isTest? "http://172.35.2.147:5004/" : "http://mf-1-finance.51mofang.cn/" ;
    String HTTP_TOKEN = isTest? "http://172.35.2.147:5006/" : "http://mf-1-login.51mofang.cn/" ;

    String HTTP_MOCK = " http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/";

    /**
     * The interface User.
     */
    interface User{
        /**
         * The constant URL_SIGN_UP.//注册
         */
        String URL_SIGN_UP = HTTP_USER + "api/user/add?";
        /**
         * The constant URL_FIND_PWD.//查找密码
         */
        String URL_FIND_PWD = HTTP_USER + "api/user/forgetpwd?";
        /**
         * The constant URL_UPDATE_PWD.//更新密码
         */
        String URL_UPDATE_PWD = HTTP_USER + "api/user/modifypwd?";
        /**
         * getOSS_STS
         */
        String URL_GET_OSS_STS = HTTP_USER + "api/User/GetUpload";
    }

    /**
     * The interface Token.
     */
    interface Token{
        /**
         * The constant URL_SIGN_IN.//获取token
         */
        String URL_SIGN_IN = HTTP_TOKEN + "api/token/audience";
        /**
         * The constant URL_REFRESH_TOKEN.//刷新
         */
        String URL_REFRESH_TOKEN = HTTP_TOKEN +  "api/token/refJwt";
        /**
         * The constant URL_SIGN_OUT.//注销
         */
        String URL_SIGN_OUT = HTTP_TOKEN + "api/token/logout";


    }

    /**
     * The interface Sms.
     */
    interface SMS{
        /**
         * The constant URL_SEND_CODE.//发送手机信息
         */
        String URL_SEND_CODE = HTTP_SMS + "api/MPValidate?";
    }

    /**
     * The interface Order.
     */
    interface Order{
        /**
         * The constant URL_ORDERS.//查看订单
         */
        String URL_ORDERS = HTTP_ORDER + "api/finance/getRechargeOrderList";
        /**
         * The constant URL_ORDER_CREATE.//生成订单
         */
        String URL_ORDER_CREATE = HTTP_ORDER + "api/finance/addRechargeOrder?";
        /**
         * The constant URL_ORDER_INFO.//订单详情
         */
        String URL_ORDER_INFO = HTTP_ORDER + "api/finance/getRechargeOrderDetails?";
        /**
         * GET /api/Finance/GetCashCouponList 获取优惠券信息
         */
        String URL_COUPON_LIST =  HTTP_ORDER + "api/Finance/GetCashCouponList?";

        String URL_ORDER_SIGN = HTTP_ORDER + "api/Finance/getSign?";
    }


    interface Dynamic{
        //        - 1、发布动态   **dynamic/publishDynamic**
        String URL_PUBLISH_DYNAMIC = HTTP_MOCK + "dynamic/publishDynamic";
        //                - 2、查看广场动态  **dynamic/squareDynamics**
        String URL_SQUARE_DYNAMICS = HTTP_MOCK + "dynamic/squareDynamics";
        //                - 3、查看朋友圈动态 	**dynamic/friendsDynamics**
        String URL_FRIENDS_DYNAMIC = HTTP_MOCK + "dynamic/friendsDynamics";
        //                - 4、评论 			**dynamic/comment**
        String URL_COMMENT = HTTP_MOCK + "dynamic/comment";
        //                - 5、点赞（取消点赞） **dynamic/isLikeDynamic**
        String URL_ISLIKE_DYNAMIC = HTTP_MOCK + "dynamic/isLikeDynamic";
        //                - 6、个人动态列表  **dynamic/PersonalDynamics**
        String URL_PERSONAL_DYNAMICS = HTTP_MOCK + "dynamic/PersonalDynamics";
        //                - 8、动态详细情况（动态+评论）**dynamic/dynamicDetail**
        String URL_DYNAMIC_DETAIL = HTTP_MOCK + "dynamic/dynamicDetail";
    }

    interface Friends{
//                - 1、查找好友   **friend/searchFriends**
        String URL_FRIEND_SEARCH = "friend/sendFriendInvitations";
//                  - 2、发送好友邀请 **friend/sendFriendInvitations**
        String URL_FRIEND_SEND_INVITATION = "friend/sendFriendInvitations";
//                - 3、删除好友 **friend/deleteFriend**
        String URL_FRIEND_DELETE = "friend/deleteFriend";
//                - 4、设置好友备注 **friend/setNickname**
        String URL_FRIEND_SET_NOTENAME = "friend/setNoteName";
//                - 5、获取好友信息及判断用户是否为好友功能**friend/friendDetail**
        String URL_FRIEND_DETAIL = "friend/friendDetail";
//                - 6、设置好友权限  **friend/setFriendPermissions**
        String URL_FRIEND_PERMISSIONS = "friend/setFriendPermissions";
//                - 7、获取好友列表 **friend/getAllFriends**
        String URL_FRIEND_LIST = "friend/getAllFriends";
//                - 8、我的追随者  **friend/getMyFollowers**
        String URL_FRIEND_FOLLOWERS = "friend/getMyFollowers";
//                - 9、查看好友相关信息  **friend/lookFriendsInfos**
        String URL_FRIEND_INFO = "friend/lookInfoWithMe";
//                - 10、接受、忽略好友邀请  **friend/dealFriendInvitations**
        String URL_FRIEND_DEAL_INVIATION = "friend/dealFriendInvitations";
//                - 11、加入黑名单   **friend/addBlacklist**
        String URL_FRIEND_ADD_BLACK = "friend/addBlacklist";

    }
}
