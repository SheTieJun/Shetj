package me.shetj.update;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy<T> implements InvocationHandler {
    private T object;

    public DynamicProxy(T object) {
        this.object = object;
    }

    @Override
    public T invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //当然这里可以对方法名进行判断过滤 if(method.getName().equals("***"))
        T result = (T) method.invoke(object,args);
        return result;
    }

    public  <T> T use(T args) {
        ClassLoader loader = args.getClass().getClassLoader();
        //动态创建代理类，需要传入一个类加载器ClassLoader；一个你希望这个代理实现的接口列表，这里要代理ILawsuit接口；
        //和一个InvocationHandler的实现，也就是前面创建的proxy。
        return (T) Proxy.newProxyInstance(loader,new Class[]{args.getClass()},this);
    }

}
