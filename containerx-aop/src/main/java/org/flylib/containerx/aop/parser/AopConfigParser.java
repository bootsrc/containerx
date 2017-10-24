package org.flylib.containerx.aop.parser;

import java.util.ArrayList;
import java.util.List;

import org.flylib.containerx.aop.constant.AopTagName;
import org.flylib.containerx.aop.model.AspectElement;
import org.flylib.containerx.aop.model.AspectListElement;
import org.flylib.containerx.aop.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AopConfigParser {
	public static List<AspectElement> getAspectList(Document doc) {
		NodeList beanList = doc.getElementsByTagName(AopTagName.ASPECT_LIST);
		if (beanList != null && beanList.getLength() > 0) {
			Node node0 = beanList.item(0);
			String xmlStr = XmlUtil.nodetoString(node0);
			System.out.println("--> DefaultDocumentLoader-getAopConfig");
			System.out.println(xmlStr);
			
			return doGetAspectList(xmlStr);
		}
		return new ArrayList<AspectElement>();
	}
    
    private static List<AspectElement> doGetAspectList(String xml) {
    	AspectListElement aopList = XmlUtil.toBean(xml, AspectListElement.class);
        List<AspectElement> list = aopList.getList();
        for(AspectElement aopElement : list) {
        	System.out.println("id=" + aopElement.getId() + ", bean=" + aopElement.getBean() 
        	+ ", target=" + aopElement.getTarget());
            System.out.println("before=" + aopElement.getBefore() + ", after=" + aopElement.getAfter());
        }
        return list;
    }
}
