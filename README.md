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

## quick start-快速上手 ##
步骤如下：<br/>
Step 1: 进入containerx根目录,编译并安装containerx-parent到本地仓库。执行如下命令：<br/>
<pre><code>
cd containerx 
mvn install
</pre></code>
就可以编译containerx-parent （包括子模块containerx），并安装到PC的本地仓库 <br/>

Step 2: 在自己的项目里添加containerx的依赖
<pre><code>
&lt;dependency&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;org.flylib.containerx&lt;/groupId &gt;
&nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;containerx&lt;/artifactId&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&lt;version&gt;0.0.2-SNAPSHOT&lt;/version&gt;
&lt;/dependency&gt;
</pre></code>

可以参考containerx-demo项目。<br/>
运行containerx-demo可以运行小测试。 <br/>


## 示例 ##
containerx-demo项目 Java代码如下：
<pre><code>
package org.flylib.containerx.demo.app;

import org.flylib.containerx.beans.factory.BeanFactory;
import org.flylib.containerx.beans.factory.XmlBeanFactory;
import org.flylib.containerx.demo.model.Person;
import org.flylib.containerx.demo.service.DemoService;

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
