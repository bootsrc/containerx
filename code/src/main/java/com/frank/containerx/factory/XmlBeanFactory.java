package com.frank.containerx.factory;

import java.util.Map;

import com.frank.containerx.container.BeanRegistry;
import com.frank.containerx.model.BeanElement;
import com.frank.containerx.parser.xml.DefaultDocumentLoader;

/**
 * 类似于Spring的一个bean container - bean容器. 简单的依赖注入功能. 可以提高开发者对Java反射机制，
 * 以及Spring依赖注入原理的理解
 * 核心方法是com.frank.containerx.container.BeanGenerator的这个方法:
 * <code>public static void inject(Object bean, Map<String, String> properties)</code><br/>
 * 
 * @author Frank Liu(liushaomingdev@163.com)
 *
 */
public class XmlBeanFactory implements BeanFactory {
	public XmlBeanFactory(String fileName) {
		try {
			Map<String, BeanElement> beanDefinitionMap = DefaultDocumentLoader.readDefinition(fileName);
			BeanRegistry.register(beanDefinitionMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Object getBean(String beanName) {
		Object bean = BeanRegistry.getSingletonBean(beanName);
		return bean;
	}
}
