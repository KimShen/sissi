package com.sissi.persistent;

import java.util.Collection;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.protocol.Element;

/**
 * XMPP节持久化服务
 * 
 * @author kim 2013-11-15
 */
public interface Persistent {

	public Persistent push(Element element);

	public Collection<Element> pull(JID jid);

	public Map<String, Object> peek(Map<String, Object> query);

	public Map<String, Object> peek(Map<String, Object> query, Map<String, Object> update);
}
