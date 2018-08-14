package shetj.me.base.configs.tag

/**
 *
 * @author shetj
 * @date 2017/9/14
 */

interface EventTags {

    interface LoginState {
        companion object {
            val SEND_LOGIN = "send_login"
            val SEND_LOGOUT = "send_logout"
            val ORDER_REFRESH = "order_refresh"
            val CUSTOM_REFRESH = "custom_refresh"
        }
    }

    /**
     * 1套餐2定制3艺人
     */
    interface OrderType {
        companion object {

            val COMBO = "1"
            val CUSTOM = "2"
            val ART = "3"
        }
    }

    /**
     * 1套餐2定制3艺人
     */
    interface Custom {
        companion object {
            val COMBO = "1"
            val ART = "2"
        }
    }

    companion object {

        val SEND_CHANGE_TEAM = "send_change_team"
        val SEND_TREAD = "send_trend"
        val TAB_SIZE = 3


        val SEND_POSITION = "send_position"
        val SEND_MAP = "send_map"
    }
}
