package com.shetj.diyalbume.rx

/**
 * 类名称：ReturnMsg<br></br>
 * 内容摘要：响应返回类型的接收对象<br></br>
 * 属性描述：<br></br>
 * 方法描述：<br></br>
 * 修改备注：   <br></br>
 * 创建时间： 2016/7/26 11:24 <br></br>
 * 公司：深圳市华移科技股份有限公司<br></br>
 *
 * @author shetj<br></br>
 */


class ReturnMsg<T> {
    //1:请求成功
    //0:请求失败
    //422:token 失效
    //500:服务器异常
    var code: Int = 0
    var message: String? = null
    //返回的接收map
    var datum: T? = null
}
