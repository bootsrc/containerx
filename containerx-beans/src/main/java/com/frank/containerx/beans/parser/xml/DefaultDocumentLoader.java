package com.frank.containerx.beans.parser.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.frank.containerx.aop.parser.AopConfigParser;
import com.frank.containerx.beans.container.BeanContainer;
import com.frank.containerx.beans.model.BeanElement;
import com.frank.containerx.beans.model.PropertyElement;

public class DefaultDocumentLoader {
	private static Document xmlFile2Doc(String fileName) {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		InputSource inputSource = null;
		Document document = null;
		try {
			InputStream inputStream = DefaultDocumentLoader.class.getClassLoader().getResourceAsStream(fileName);
			inputSource = new InputSource(inputStream);	
			document = docBuilderFactory.newDocumentBuilder().parse(inputSource);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
	
	public static Map<String, BeanElement> readDefinition(String fileName) throws Exception {
		Map<String, BeanElement> beanDefinitionMap = new HashMap<String, BeanElement>();
		if (fileName == null || fileName.length() < 1) {
			throw new IllegalArgumentException("fileName should not be empty!");
		}
		Document doc = DefaultDocumentLoader.xmlFile2Doc(fileName);
		
		NodeList beanList = doc.getElementsByTagName("bean");
		if (beanList.getLength() > 0) {
			for (int beanIndex = 0; beanIndex < beanList.getLength(); beanIndex++) {
				Node beanNode = beanList.item(beanIndex);
				BeanElement beanElement = parseOneBean(beanNode);
				beanDefinitionMap.put(beanElement.getId(), beanElement);
			}
		}
		
		//处理AOP
		getAopConfig(doc);
		
		return beanDefinitionMap;
	}
	
	private static BeanElement parseOneBean(Node beanNode) {
		BeanElement beanElement = new BeanElement(); 
		Map<String, String> properties = new HashMap<String, String>();
		
		NamedNodeMap attrs = beanNode.getAttributes();
		
		if (attrs.getLength() > 0) {
			for (int attrIndex = 0 ; attrIndex < attrs.getLength(); attrIndex++) {
				String attrName = attrs.item(attrIndex).getNodeName();
				String attrValue = attrs.item(attrIndex).getNodeValue();
				if ("id".equals(attrName)) {
					beanElement.setId(attrValue);
				} else if ("class".equals(attrName)){
					beanElement.setClassName(attrValue);
				}
			}
		}

		NodeList propertyList = beanNode.getChildNodes();
		if (propertyList != null && propertyList.getLength() > 0) {
			for(int propIndex = 0; propIndex < propertyList.getLength(); propIndex++) {
				NamedNodeMap propertyData = propertyList.item(propIndex).getAttributes();
				if (propertyData != null && propertyData.getLength() > 0){
					PropertyElement propertyElement = new PropertyElement();
					for (int i0 = 0; i0 < propertyData.getLength(); i0++) {
						String attrName0 = propertyData.item(i0).getNodeName();
						String attrValue0 = propertyData.item(i0).getNodeValue();
						if ("name".equals(attrName0)) {
							propertyElement.setName(attrValue0);
						} else if ("value".equals(attrName0)) {
							propertyElement.setValue(attrValue0);
						}
					}
					properties.put(propertyElement.getName(), propertyElement.getValue());
				}
			}
		}
		
		beanElement.setProperties(properties);
		return beanElement;
	}
	
	private static String getAopConfig(Document doc) {
		NodeList beanList = doc.getElementsByTagName("aop-list");
		Node node0 = beanList.item(0);
		String xmlStr = nodetoString(node0);
		System.out.println("--> DefaultDocumentLoader-getAopConfig");
		System.out.println(xmlStr);
		
		AopConfigParser.getAopConfig(xmlStr);
		return xmlStr;
	}
	
//	public static String aopConfigTest(String fileName) throws Exception {
//		Map<String, BeanElement> beanDefinitionMap = new HashMap<String, BeanElement>();
//		if (fileName == null || fileName.length() < 1) {
//			throw new IllegalArgumentException("fileName should not be empty!");
//		}
//		Document doc = DefaultDocumentLoader.xmlFile2Doc(fileName);
//		getAopConfig(doc);
//		return null;
//	}
	
//	public static String nodeToString(Document doc) throws TransformerFactoryConfigurationError, TransformerException{   
//        DOMSource source = new DOMSource(doc);   
//         StringWriter writer = new StringWriter();   
//         Result result = new StreamResult(writer);   
//         Transformer transformer = TransformerFactory.newInstance().newTransformer();   
//         transformer.setOutputProperty(OutputKeys.INDENT, "yes");   
//         transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "yes");   
//         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//         transformer.transform(source, result);   
//         return (writer.getBuffer().toString());   
//    }  
	
	/** 
     * 将传入的一个DOM Node对象输出成字符串。如果失败则返回一个空字符串""。 
     *  
     * @param node 
     *            DOM Node 对象。 
     * @return a XML String from node 
     */  
    public static String nodetoString(Node node) {  
        Transformer transformer = null;  
        String result = null;  
        if (node == null) {  
            throw new IllegalArgumentException();  
        }  
        try {  
            transformer = TransformerFactory.newInstance().newTransformer();  
        } catch (Exception e) {  
            throw new RuntimeException(e.getMessage());  
        }  
        if (transformer != null) {  
            try {  
                StringWriter sw = new StringWriter();  
                transformer  
                        .transform(new DOMSource(node), new StreamResult(sw));  
                return sw.toString();  
            } catch (TransformerException te) {  
                throw new RuntimeException(te.getMessage());  
            }  
        }  
        return result;  
    }  
	
	public static void main(String[] args) {
		try {
//			readDefinition("beans.xml");
			
//			aopConfigTest("beans.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
