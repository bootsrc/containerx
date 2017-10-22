package com.frank.containerx.aop.parser;

import java.io.InputStream;
import java.util.List;

import com.frank.containerx.aop.model.AopElement;
import com.frank.containerx.aop.model.AopListElement;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class AopConfigParser {
	private static <T> T toBean(String xmlStr, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        @SuppressWarnings("unchecked")
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }
    
    public static List<AopElement> getAopConfig(String xml) {
    	AopListElement aopList = toBean(xml, AopListElement.class);
        List<AopElement> list = aopList.getList();
        for(AopElement aopElement : list) {
        	System.out.println("id=" + aopElement.getId() + ", intercepter=" + aopElement.getInterceptor());
            System.out.println("before=" + aopElement.getBefore() + ", after=" + aopElement.getAfter());
        }
        return list;
    }
}
