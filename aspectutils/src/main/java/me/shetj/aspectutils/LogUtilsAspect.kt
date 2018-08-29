//package me.shetj.aspectutils
//
//import me.shetj.base.tools.json.GsonKit
//import me.shetj.base.tools.time.TimeUtil
//import org.aspectj.lang.JoinPoint
//import org.aspectj.lang.annotation.*
//import org.xutils.common.util.LogUtil
//
//
///**
// *
// * <b>@packageName：</b> me.shetj.aspectutils<br>
// * <b>@author：</b> shetj<br>
// * <b>@createTime：</b> 2018/8/29 0029<br>
// * <b>@company：</b><br>
// * <b>@email：</b> 375105540@qq.com<br>
// * <b>@describe</b><br>
// */
//@Aspect
//class LogUtilsAspect{
//    /**
//     * 第一步 切入点
//     */
//    @Pointcut("execution(* *..updateView(..)))")
//    fun methodAnnLogUtil( ){
//
//    }
//
//
//    @Before("methodAnnLogUtil()")
//    fun beforeMethod(joinPoint:JoinPoint ){
//        LogUtil.i("start time = ${TimeUtil.time}")
//        val targetName = joinPoint.target.javaClass.name // 目标类全路径名
//        val clazz = Class.forName(targetName) // 反射得到目标类
//
//        LogUtil.i("  : targetName = $targetName ")
//        val methodName = joinPoint.signature.name // 目标方法名（正在访问的方法）
//
//        LogUtil.i("   : methodName = $methodName ")
//
//        val arguments = joinPoint.args // 方法参数：数组类型
//        LogUtil.i("   : arguments = ${GsonKit.objectToJson(arguments)} ")
//
//        val methods = clazz.methods // 反射得到目标类的所有方法
//
////        for (method in methods) {
////            val clazzs = method.parameterTypes
////            if (clazzs.size == arguments.size) {
////                //方法上的注解内容提取
////                val doType = method.getAnnotation(LogUtils::class.java).logUtils
////                LogUtil.i("LogUtils = $doType")
////                val doName = method.getAnnotation(MPermission::class.java).value
////                LogUtil.i("MPermission = $doName")
////                break
////            }
////        }
//    }
//
//    @After("methodAnnLogUtil()")
//    fun afterMethod(joinPoint: JoinPoint){
//        LogUtil.i("end time = ${TimeUtil.time}")
//    }
//    /**
//     * @AfterThrowing
//     * @param throwable
//     */
//    @AfterThrowing(pointcut = "call(* *..*(..))", throwing = "throwable")
//    fun anyFuncThrows(throwable: Throwable) {
//        LogUtil.i("Throws: ${throwable.message}", throwable)
//    }
//}