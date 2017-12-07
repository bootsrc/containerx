package io.github.flylib.containerx.demo.service;

/**
 * 所有需要使用AOP的类都要实现一个保护自己所有方法的interface才行
 * @author Frank Liu(liushaomingdev@163.com)
 *
 */
public class DemoServiceImpl implements DemoService {
	@Override
	public void doSomething() {
		System.out.println("---DemoServiceImpl.doSomething() done---");
	}
}
