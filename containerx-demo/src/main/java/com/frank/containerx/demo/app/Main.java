package com.frank.containerx.demo.app;

import com.frank.containerx.beans.factory.BeanFactory;
import com.frank.containerx.beans.factory.XmlBeanFactory;
import com.frank.containerx.demo.model.Person;
import com.frank.containerx.demo.service.DemoService;

/**
 * containerx的demo
 * @author Frank Liu(liushaomingdev@163.com)
 *
 */
public class Main {
	public static void main(String[] args) {
		iocDemo();
	}
	
	private static void iocDemo() {
		BeanFactory beanFactory = new XmlBeanFactory("beans.xml");
		Person person = (Person)beanFactory.getBean("myPerson");
		
		System.out.println("name=" + person.getName());
		System.out.println("address=" + person.getAddress());
		
		Person person1 = (Person)beanFactory.getBean("myPerson");
		System.out.println("person=" + person);
		System.out.println("person1=" + person1);
		System.out.println("person == person1? " + (person == person1));
		
		aopDemo(beanFactory);
	}
	
	private static void aopDemo(BeanFactory beanFactory) {
		DemoService demoService = (DemoService)beanFactory.getBean("demoService");
		System.out.println("\n\n---AOP demo---");
		demoService.doSomething();
	}
}
