package io.github.flylib.containerx.aop.util;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlUtil {
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
				transformer.transform(new DOMSource(node), new StreamResult(sw));
				return sw.toString();
			} catch (TransformerException te) {
				throw new RuntimeException(te.getMessage());
			}
		}
		return result;
	}
	
	public static <T> T toBean(String xmlStr, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        @SuppressWarnings("unchecked")
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }
}
