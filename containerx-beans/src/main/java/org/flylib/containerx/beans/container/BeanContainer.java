package org.flylib.containerx.beans.container;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BeanContainer {
	private static final ConcurrentMap<String, Object> singletonBeans = new ConcurrentHashMap<String, Object>();
	
	public static Object get(String key) {
		return singletonBeans.get(key);
	}
	
	public static Object putIfAbsent(String key, Object value) {
		return singletonBeans.putIfAbsent(key, value);
	}
	
	public static Object put(String key, Object value) {
		return singletonBeans.put(key, value);
	}
}
