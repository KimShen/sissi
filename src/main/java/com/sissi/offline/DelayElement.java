package com.sissi.offline;

import java.util.Map;

import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
public interface DelayElement {

	public Map<String, Object> write(Element element);

	public Element read(Map<String, Object> element);

	public Boolean isSupport(Element element);

	public Boolean isSupport(Map<String, Object> element);
}
