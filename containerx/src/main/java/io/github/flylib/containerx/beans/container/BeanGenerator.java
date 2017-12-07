package io.github.flylib.containerx.beans.container;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.github.flylib.containerx.beans.model.BeanElement;

public class BeanGenerator {
	/**
	 * 核心注入方法
	 * <code>method.invoke(bean, methodMap.get(methodName));</code>
	 * @param bean
	 * @param properties
	 */
	public static void inject(Object bean, Map<String, String> properties) {
		Map<String, String> methodMap = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			String configName = entry.getKey();
			String configValue = entry.getValue();
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
	
	public static Object newBean(BeanElement definition) {
		String className = definition.getClassName();
		try {
			Class<?> clazz = Class.forName(className, true, getClassLoader());
			Object bean = clazz.newInstance();
			inject(bean, definition.getProperties());
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	private static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        classLoader = BeanGenerator.class.getClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        return classLoader;
    }
}
