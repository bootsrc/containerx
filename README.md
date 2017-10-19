#containerx
containerx是类似于Spring的一个bean container - bean容器. 简单的依赖注入功能. 可以提高开发者对Java反射机制， <br/>
 以及Spring依赖注入原理的理解 <br/>
主要开发者是Frank Liu刘少明 (liushaomingdev@163.com) <br/>
 核心方法是<code>inject(Object bean, Map<String, Object> properties)</code><br/>
而其中最核心的一行代码是
<pre><code>
method.invoke(bean, methodMap.get(methodName));
</pre></code>
