package com.frank.containerx.parser.xml;

import java.util.HashSet;
import java.util.Set;

public class BeanValidation {
	/**
	 * beans.xml文件中bean的格式应该是这样的
	 * <code></code>
	 * <bean id="myBean" class="com.frank.model.SomeClass">
	 */
	public static final Set<String> BEAN_ATTRIBUTE_SET = new HashSet<String>();
	public static final Set<String> PROPERTY_ATTRIBUTE_SET = new HashSet<String>();
	
	static {
		BEAN_ATTRIBUTE_SET.add("id");
		BEAN_ATTRIBUTE_SET.add("class");
		
		PROPERTY_ATTRIBUTE_SET.add("name");
		PROPERTY_ATTRIBUTE_SET.add("value");
	}
	
	
}
