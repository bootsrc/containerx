package com.frank.containerx.container;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BeanContainer {
	public static final ConcurrentMap<String, Object> singletonBeans = new ConcurrentHashMap<String, Object>();
}
