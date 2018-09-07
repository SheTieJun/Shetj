package me.shetj.aspect.debug;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import me.shetj.base.tools.time.TimeUtil;
import timber.log.Timber;

/**
 * <b>@packageName：</b> me.shetj.aspectutils<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/8/29 0029<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
@Aspect
public class DebugAspect {

	@Pointcut("execution(@me.shetj.aspect.debug.DebugTrace * *())")
	public void DebugTraceMethod() {
	}


	@Around("DebugTraceMethod()")
	public void beforeDebugTraceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		Timber.i( methodName+" start time = " + TimeUtil.getTime());
//		// 目标类全路径名
//		String targetName = joinPoint.getTarget().getClass().getName();
//		Class clazz = Class.forName(targetName); // 反射得到目标类
//// 目标方法名（正在访问的方法）
//		LogUtil.i("  : targetName = " + targetName);

//// 方法参数：数组类型
//		Object[] arguments = joinPoint.getArgs();
//		LogUtil.i("   : arguments =  " + GsonKit.objectToJson(arguments));
//// 反射得到目标类的所有方法
//		Method[] methods = clazz.getMethods();
//		LogUtil.i("   : arguments =  " + GsonKit.objectToJson(methods));
//        for (method in methods) {
//            val clazzs = method.parameterTypes
//            if (clazzs.size == arguments.size) {
//                //方法上的注解内容提取
//                val doType = method.getAnnotation(LogUtils::class.java).logUtils
//                LogUtil.i("LogUtils = $doType")
//                val doName = method.getAnnotation(MPermission::class.java).value
//                LogUtil.i("MPermission = $doName")
//                break
//            }
//        }
		joinPoint.proceed();
		Timber.i( methodName+ " end time = " + TimeUtil.getTime());
	}

}
