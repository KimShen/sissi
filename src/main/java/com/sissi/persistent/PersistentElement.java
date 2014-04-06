package com.sissi.persistent;

import java.util.Map;

import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
public interface PersistentElement {

	public Map<String, Object> query(Element element);

	public Map<String, Object> write(Element element);

	public Element read(Map<String, Object> element);

	public boolean isSupport(Element element);

	public boolean isSupport(Map<String, Object> element);

	public Class<? extends Element> support();
}
