package com.meet.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.meet.base.EventMessage;
import com.meet.base.TextMessage;
import com.thoughtworks.xstream.XStream;



/**
 * 
 * @author Administrator
 */
public class MessageUtil {

	/**
	 * 
	 * 微信页面传过来的数据是xml类型，所以不能直接getParameter，需要转成map类型，才能获取参数值
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		
		Map<String, String> map = new HashMap<String, String>();
		
		SAXReader reader  = new SAXReader();
		
		InputStream in = request.getInputStream();
		
		Document document = reader.read(in);
		
		Element root = document.getRootElement();
		
		List<Element> list = root.elements();
		
		for(Element element : list) {
			map.put(element.getName(), element.getText());
		}
		
		in.close();
		
		return map;
	}
	
//	将消息发给用户同样需要传输xml类型
	public static String toXML(TextMessage textMessage) {
		
		XStream xStream = new XStream();
		xStream.alias("xml", textMessage.getClass());
		String xml = xStream.toXML(textMessage);
		return xml;
	}
	
	
	@Test
	public void test1() {
		TextMessage message = new TextMessage();
		message.setContent("123");
		message.setFromUserName("123123");
		String xml = MessageUtil.toXML(message);
		System.out.println(xml);
	}
}
