package com.frank.containerx.container;

import java.util.Map;

import com.frank.containerx.model.BeanElement;

public class BeanRegistry {
	public static void register(Map<String, BeanElement> beanDefinitionMap) {
		for (Map.Entry<String, BeanElement> entry : beanDefinitionMap.entrySet()) {
			BeanElement definition = entry.getValue();
			Object bean = BeanGenerator.newBean(definition);
			BeanContainer.singletonBeans.putIfAbsent(entry.getKey(), bean);
		}
	}
	
	public static Object getSingletonBean(String beanName) {
		return BeanContainer.singletonBeans.get(beanName);
	}
}
