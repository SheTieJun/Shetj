package me.shetj.aspect.network;

import android.app.Application;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.app.DeviceUtils;
import me.shetj.base.tools.app.Utils;
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
public class NetworkAspect {

	@Pointcut("execution(@me.shetj.aspect.network.CheckNetwork * *())")
	public void CheckNetworkMethod() {
	}


	@Around("CheckNetworkMethod()")
	public void beforeCheckNetworkMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean hasInternet = DeviceUtils.hasInternet(Utils.getApp().getApplicationContext());
		if (hasInternet) {
			joinPoint.proceed();
		}else {
			ArmsUtils.makeText("无法连接网络~！");
		}
	}

}
