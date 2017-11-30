package org.flylib.containerx.beans.container;

import java.util.Map;

import org.flylib.containerx.beans.model.BeanElement;

public class BeanRegistry {
	public static void register(Map<String, BeanElement> beanDefinitionMap) {
		for (Map.Entry<String, BeanElement> entry : beanDefinitionMap.entrySet()) {
			BeanElement definition = entry.getValue();
			Object bean = BeanGenerator.newBean(definition);
			BeanContainer.putIfAbsent(entry.getKey(), bean);
		}
	}
	
	public static Object getSingletonBean(String beanName) {
		return BeanContainer.get(beanName);
	}
}
