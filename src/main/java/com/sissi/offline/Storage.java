package com.sissi.offline;

import java.util.Map;

import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
public interface Storage {

	public Map<String, Object> write(Element element);

	public Element read(Map<String, Object> storage);

	public Boolean isSupport(Element element);

	public Boolean isSupport(Map<String, Object> storage);
}
