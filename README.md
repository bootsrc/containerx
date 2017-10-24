# containerx #
## 简介 ##
containerx是一款迷你型的bean容器，IoC框架。是作者学习了Spring源码后，为了进一步学习Spring原理而自行开发的框架。
极少使用其他的第三方jar包。
## 用途 ##
主要用于学习Spring/AOP原理, 或者在自己项目中用于轻量级IoC。
## 开发者 ##
刘少明(Frank Liu) &nbsp;&nbsp;&nbsp; git [https://github.com/frank-liu-1](https://github.com/frank-liu-1 "https://github.com/frank-liu-1")
&nbsp;&nbsp;&nbsp; 邮箱 liushaomingdev@163.com <br/>
博客： csdn &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; [http://blog.csdn.net/lsm135](http://blog.csdn.net/lsm135 "http://blog.csdn.net/lsm135") <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 开源中国 [https://my.oschina.net/u/3051910/blog](https://my.oschina.net/u/3051910/blog "https://my.oschina.net/u/3051910/blog")
<br/>

## 实现的功能 ##
1. setter注入（目前基于xml配置bean，未来会支持注解配置bean）;
2. 单例bean的注册和获取;
3. AOP面向切面编程（基于xml配置）;

## 示例 ##
containerx-demo项目 Java代码如下：
<pre><code>
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
</pre></code>
运行结果如下：
<pre><code>
name=Frank Liu
address=Shanghai,China(中国-上海)
person=com.frank.containerx.demo.model.Person@33909752
person1=com.frank.containerx.demo.model.Person@33909752
person == person1? true
...
AOP日志
...
</pre></code>

## 框架原理 ##
 核心方法是<code>inject(Object bean, Map<String, Object> properties)</code><br/>
而其中核心的一行代码是
<pre><code>
method.invoke(bean, methodMap.get(methodName));
</pre></code>

## 注意事项 ##
cd containerx (进入containerx目录)
mvn install
就可以编译containerx-parent （包括下面的containerx-beans,containerx-aop,containerx-demo这3个module） <br/>

运行containerx-demo可以运行小测试。 <br/>
如果想在自己的项目里使用containerx，参考containerx-demo， 添加containerx-parent的maven依赖就行了。<br/>
