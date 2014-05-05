package com.sissi.persistent;

import java.util.Map;

import com.sissi.protocol.Element;

/**
 * XMPP节持久化策略
 * 
 * @author kim 2013-11-15
 */
public interface PersistentElement {

	public Map<String, Object> query(Element element);

	public Map<String, Object> write(Element element);

	public Element read(Map<String, Object> element);

	/**
	 * 是否支持
	 * 
	 * @param element
	 * @return
	 */
	public boolean isSupport(Element element);

	/**
	 * 是否支持
	 * 
	 * @param element
	 * @return
	 */
	public boolean isSupport(Map<String, Object> element);

	/**
	 * 支持的XMPP节类型
	 * 
	 * @return
	 */
	public Class<? extends Element> support();
}
