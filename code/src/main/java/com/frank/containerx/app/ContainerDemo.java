package com.frank.containerx.app;

import java.util.ArrayList;

import com.frank.containerx.container.MyBeanFactory;
import com.frank.containerx.model.Person;

public class ContainerDemo {
	public static void main(String[] args) {
		Person bean = MyBeanFactory.getBean(Person.class);
		System.out.println("name=" + bean.getName());
	}
}
