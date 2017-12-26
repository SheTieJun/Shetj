package cn.shetj.base.http.callback;


import cn.shetj.base.tools.app.LogUtil;

/**
 * 请求回调
 * @param <ResultType>
 */
public class MCallBack<ResultType> {

  /**
   *  public static int RESULT_OK = 200;
   *  public static int RESULT_EXE_OK = 201;
   *  public static int RESULT_INVALID_PARAMS = 400;//参数不符合 API 的要求、或者数据格式验证没有通过
   *  public static int RESULT_UNAUTHORIZED = 401;//信息超时，比如登录很久token过期
   *  public static int RESULT_NO_PRISS = 403;
   *  public static int RESULT_NOT_FIND = 404;
   *  public static int RESULT_API_ERROR = 500;
   * @param result
   */
  public void onSuccess(ResultType result){
    LogUtil.show("result","result="+result.toString());
  }

  /**
   *  //可以根据公司的需求进行统一的请求网络失败的逻辑处理
   */

  public void onError(int errorCode,String msg) {
  }



  public void onFinished() {
  }


}
