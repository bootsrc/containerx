package com.frank.containerx.container;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 类似于Spring的一个bean container - bean容器. 简单的依赖注入功能. 可以提高开发者对Java反射机制，
 * 以及Spring依赖注入原理的理解
 * 核心方法是<code>inject(Object bean, Map<String, Object> properties)</code><br/>
 * 
 * @author Frank Liu(liushaomingdev@163.com)
 *
 */
public class MyBeanFactory {
	public static <T> T getBean(Class<T> thisClazz) {
		Map<String, Object> properties = getProperties();
		String className = "com.frank.containerx.model.Person";
		try {
			Class<?> clazz = Class.forName(className, true, getClassLoader());
			Object bean = clazz.newInstance();
			inject(bean, properties);
			return (T)bean;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	private static Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("name", "Frank Liu");
		return properties;
	}
	
	private static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        classLoader = MyBeanFactory.class.getClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        return classLoader;
    }

	/**
	 * 核心注入方法
	 * <code>method.invoke(bean, methodMap.get(methodName));</code>
	 * @param bean
	 * @param properties
	 */
	private static void inject(Object bean, Map<String, Object> properties) {
		Set<Map.Entry<String, Object>> set = properties.entrySet();
		Map<String, Object> methodMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : set) {
			String configName = entry.getKey();
			Object configValue = entry.getValue();
			String configMethodName = "";
			if (configName != null && configName.length() > 0) {
				configMethodName = "set" + String.valueOf(configName.charAt(0)).toUpperCase() + configName.substring(1);
			}
			methodMap.put(configMethodName, configValue);
		}
		
		Class clazz = bean.getClass();
		for (Method method : clazz.getMethods()) {
			String methodName = method.getName();
			if (methodName.startsWith("set") && method.getParameterTypes().length == 1
					&& Modifier.isPublic(method.getModifiers())
					&& methodMap.containsKey(methodName)) {
				try {
					method.invoke(bean, methodMap.get(methodName));
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
	}
}
