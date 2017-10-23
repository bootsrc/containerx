package com.frank.containerx.beans.factory;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import com.frank.containerx.aop.model.AspectElement;
import com.frank.containerx.aop.parser.AopConfigParser;
import com.frank.containerx.aop.proxy.AopBeanProxy;
import com.frank.containerx.beans.container.BeanContainer;
import com.frank.containerx.beans.container.BeanRegistry;
import com.frank.containerx.beans.model.BeanElement;
import com.frank.containerx.beans.parser.xml.DefaultDocumentLoader;

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
			Document doc = DefaultDocumentLoader.xmlFile2Doc(fileName);
			
			Map<String, BeanElement> beanDefinitionMap = DefaultDocumentLoader.readDefinition(doc);
			BeanRegistry.register(beanDefinitionMap);
			//
			getAopConfig(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Object getBean(String beanName) {
		Object bean = BeanRegistry.getSingletonBean(beanName);
		return bean;
	}
	
	private static void getAopConfig(Document doc) {
		List<AspectElement> aspectList = AopConfigParser.getAspectList(doc);
		if (aspectList != null && aspectList.size() > 0) {
			for (AspectElement aspect : aspectList) {
				String targetBeanName = aspect.getTarget();
				Object target = BeanContainer.get(aspect.getTarget());
				Object proxyBean = BeanContainer.get(aspect.getBean());
				if (target != null && proxyBean != null) {
					Object proxiedBean = new AopBeanProxy().bind(target, proxyBean, aspect);
					// 用代理bean去代替bean容器中原有的ban
					BeanContainer.put(aspect.getTarget(), proxiedBean);
					System.out.println("用代理bean去代替bean容器中原有的ban 完毕.");
				}
			}
		}
	}
	
	private static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        classLoader = XmlBeanFactory.class.getClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        return classLoader;
    }
}
