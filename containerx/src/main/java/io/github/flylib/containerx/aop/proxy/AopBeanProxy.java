package io.github.flylib.containerx.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import io.github.flylib.containerx.aop.model.AspectElement;

public class AopBeanProxy implements InvocationHandler {
	// 调用对象
	private Object proxy;
	// 目标对象
	private Object target;
	
	private AspectElement aspectDefinition;

	/**
	 * 
	 * @param target 被代理的对象
	 * @param proxy 拦截器（<aop:aspect>的bean指明的bean
	 * @return
	 */
	public Object bind(Object target, Object proxy, AspectElement aspectDefinition) {
		this.target = target;
		this.proxy = proxy;
		this.aspectDefinition = aspectDefinition;
		return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target.getClass().getInterfaces(),
				this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		// 反射得到操作者的实例
		Class clazz = this.proxy.getClass();
		// 反射得到操作者的Start方法
//		Method onBefore = clazz.getDeclaredMethod(aspectDefinition.getBefore(), new Class[] { Method.class });
		Method onBefore = clazz.getDeclaredMethod(aspectDefinition.getBefore(), null);
		// 反射执行start方法
//		onBefore.invoke(this.proxy, new Object[] { this.proxy.getClass() });
		onBefore.invoke(this.proxy, null );
		// 执行要处理对象的原本方法
		method.invoke(this.target, args);
		// 反射得到操作者的end方法
//		Method onAfter = clazz.getDeclaredMethod(aspectDefinition.getAfter(), new Class[] { Method.class });
		Method onAfter = clazz.getDeclaredMethod(aspectDefinition.getAfter(), null);
		// 反射执行end方法
//		onAfter.invoke(this.proxy, new Object[] { method });
		onAfter.invoke(this.proxy, null);
		return result;
	}
}
