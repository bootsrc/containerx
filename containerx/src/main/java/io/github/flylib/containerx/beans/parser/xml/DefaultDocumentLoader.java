package io.github.flylib.containerx.beans.parser.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import io.github.flylib.containerx.aop.model.AspectElement;
import io.github.flylib.containerx.aop.parser.AopConfigParser;
import io.github.flylib.containerx.aop.proxy.AopBeanProxy;
import io.github.flylib.containerx.beans.container.BeanContainer;
import io.github.flylib.containerx.beans.container.BeanGenerator;
import io.github.flylib.containerx.beans.model.BeanElement;
import io.github.flylib.containerx.beans.model.PropertyElement;

public class DefaultDocumentLoader {
	public static Document xmlFile2Doc(String fileName) {
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
	
	@Deprecated
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
		
		return beanDefinitionMap;
	}
	
	public static Map<String, BeanElement> readDefinition(Document doc) throws Exception {
		Map<String, BeanElement> beanDefinitionMap = new HashMap<String, BeanElement>();
		
		NodeList beanList = doc.getElementsByTagName("bean");
		if (beanList.getLength() > 0) {
			for (int beanIndex = 0; beanIndex < beanList.getLength(); beanIndex++) {
				Node beanNode = beanList.item(beanIndex);
				BeanElement beanElement = parseOneBean(beanNode);
				beanDefinitionMap.put(beanElement.getId(), beanElement);
			}
		}
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
}
