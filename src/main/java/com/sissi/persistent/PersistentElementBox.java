package com.sissi.persistent;

import java.util.Collection;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
public interface PersistentElementBox {

	public final static String fieldActivate = "activate";

	public final static String fieldClass = "class";

	public final static String fieldDelay = "delay";

	public final static String fieldFrom = "from";

	public final static String fieldHost = "host";

	public final static String fieldType = "type";

	public final static String fieldSid = "sid";

	public final static String fieldTo = "to";

	public final static String fieldId = "id";

	public Collection<Element> pull(JID jid);

	public PersistentElementBox push(Element element);

	public Map<String, Object> peek(Map<String, Object> query, Map<String, Object> update);
}
