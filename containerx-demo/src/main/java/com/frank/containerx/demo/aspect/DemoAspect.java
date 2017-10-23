package com.frank.containerx.demo.aspect;

import java.lang.reflect.Method;

public class DemoAspect {
//	public void beforeMethod(Method method) {
//		System.out.println("---" + method.getName() + " before---");
//	}
//	
//	public void afterMethod(Method method) {
//		 System.out.println("---" + method.getName() + " after---");
//	}
//	
	public void beforeMethod() {
		System.out.println("---aop log-" + " before---");
	}
	
	public void afterMethod() {
		 System.out.println("---aop log-" + " after---");
	}
}
