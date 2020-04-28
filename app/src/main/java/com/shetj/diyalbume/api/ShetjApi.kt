package com.shetj.diyalbume.api


/**
 * Created by admin
 * on 2017/9/28.
 */

interface ShetjApi {

    /**
     * The interface User.
     */
    interface User {
        companion object {
            /**
             * The constant URL_SIGN_UP.//注册
             */
            val URL_SIGN_UP = HTTP_USER + "api/user/add?"
            /**
             * The constant URL_FIND_PWD.//查找密码
             */
            val URL_FIND_PWD = HTTP_USER + "api/user/forgetpwd?"
            /**
             * The constant URL_UPDATE_PWD.//更新密码
             */
            val URL_UPDATE_PWD = HTTP_USER + "api/user/modifypwd?"
            /**
             * getOSS_STS
             */
            val URL_GET_OSS_STS = HTTP_USER + "api/User/GetUpload"
        }
    }

    /**
     * The interface Token.
     */
    interface Token {
        companion object {
            /**
             * The constant URL_SIGN_IN.//获取token
             */
            val URL_SIGN_IN = HTTP_TOKEN + "api/token/audience"
            /**
             * The constant URL_REFRESH_TOKEN.//刷新
             */
            val URL_REFRESH_TOKEN = HTTP_TOKEN + "api/token/refJwt"
            /**
             * The constant URL_SIGN_OUT.//注销
             */
            val URL_SIGN_OUT = HTTP_TOKEN + "api/token/logout"
        }


    }

    /**
     * The interface Sms.
     */
    interface SMS {
        companion object {
            /**
             * The constant URL_SEND_CODE.//发送手机信息
             */
            val URL_SEND_CODE = HTTP_SMS + "api/MPValidate?"
        }
    }

    /**
     * The interface Order.
     */
    interface Order {
        companion object {
            /**
             * The constant URL_ORDERS.//查看订单
             */
            val URL_ORDERS = HTTP_ORDER + "api/finance/getRechargeOrderList"
            /**
             * The constant URL_ORDER_CREATE.//生成订单
             */
            val URL_ORDER_CREATE = HTTP_ORDER + "api/finance/addRechargeOrder?"
            /**
             * The constant URL_ORDER_INFO.//订单详情
             */
            val URL_ORDER_INFO = HTTP_ORDER + "api/finance/getRechargeOrderDetails?"
            /**
             * GET /api/Finance/GetCashCouponList 获取优惠券信息
             */
            val URL_COUPON_LIST = HTTP_ORDER + "api/Finance/GetCashCouponList?"

            val URL_ORDER_SIGN = HTTP_ORDER + "api/Finance/getSign?"
        }
    }


    interface Dynamic {
        companion object {
            //        - 1、发布动态   **dynamic/publishDynamic**
            val URL_PUBLISH_DYNAMIC = HTTP_MOCK + "dynamic/publishDynamic"
            //                - 2、查看广场动态  **dynamic/squareDynamics**
            val URL_SQUARE_DYNAMICS = HTTP_MOCK + "dynamic/squareDynamics"
            //                - 3、查看朋友圈动态 	**dynamic/friendsDynamics**
            val URL_FRIENDS_DYNAMIC = HTTP_MOCK + "dynamic/friendsDynamics"
            //                - 4、评论 			**dynamic/comment**
            val URL_COMMENT = HTTP_MOCK + "dynamic/comment"
            //                - 5、点赞（取消点赞） **dynamic/isLikeDynamic**
            val URL_ISLIKE_DYNAMIC = HTTP_MOCK + "dynamic/isLikeDynamic"
            //                - 6、个人动态列表  **dynamic/PersonalDynamics**
            val URL_PERSONAL_DYNAMICS = HTTP_MOCK + "dynamic/PersonalDynamics"
            //                - 8、动态详细情况（动态+评论）**dynamic/dynamicDetail**
            val URL_DYNAMIC_DETAIL = HTTP_MOCK + "dynamic/dynamicDetail"
        }
    }

    interface Friends {
        companion object {
            //                - 1、查找好友   **friend/searchFriends**
            val URL_FRIEND_SEARCH = "friend/sendFriendInvitations"
            //                  - 2、发送好友邀请 **friend/sendFriendInvitations**
            val URL_FRIEND_SEND_INVITATION = "friend/sendFriendInvitations"
            //                - 3、删除好友 **friend/deleteFriend**
            val URL_FRIEND_DELETE = "friend/deleteFriend"
            //                - 4、设置好友备注 **friend/setNickname**
            val URL_FRIEND_SET_NOTENAME = "friend/setNoteName"
            //                - 5、获取好友信息及判断用户是否为好友功能**friend/friendDetail**
            val URL_FRIEND_DETAIL = "friend/friendDetail"
            //                - 6、设置好友权限  **friend/setFriendPermissions**
            val URL_FRIEND_PERMISSIONS = "friend/setFriendPermissions"
            //                - 7、获取好友列表 **friend/getAllFriends**
            val URL_FRIEND_LIST = "friend/getAllFriends"
            //                - 8、我的追随者  **friend/getMyFollowers**
            val URL_FRIEND_FOLLOWERS = "friend/getMyFollowers"
            //                - 9、查看好友相关信息  **friend/lookFriendsInfos**
            val URL_FRIEND_INFO = "friend/lookInfoWithMe"
            //                - 10、接受、忽略好友邀请  **friend/dealFriendInvitations**
            val URL_FRIEND_DEAL_INVIATION = "friend/dealFriendInvitations"
            //                - 11、加入黑名单   **friend/addBlacklist**
            val URL_FRIEND_ADD_BLACK = "friend/addBlacklist"
        }

    }

    companion object {
        val isTest = false
        val HTTP_SMS = if (isTest) "http://172.35.2.147:5005/" else "http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/"
        val HTTP_USER = if (isTest) "http://172.35.2.147:5000/" else "http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/"
        val HTTP_ORDER = if (isTest) "http://172.35.2.147:5004/" else "http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/"
        val HTTP_TOKEN = if (isTest) "http://172.35.2.147:5006/" else "http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/"

        val HTTP_MOCK = " http://172.35.2.152/mock/59f179fdc615ca5028cc1309/mofang_v3/"
    }
}
