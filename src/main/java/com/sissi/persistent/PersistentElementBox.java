package com.sissi.persistent;

import java.util.Collection;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
public interface PersistentElementBox {
	
	public final static String fieldResend = "resend";

	public final static String fieldDelay = "delay";

	public final static String fieldSize = "size";

	public final static String fieldHost = "host";

	public final static String fieldSid = "sid";

	public final static String fieldAck = "ack";

	public final static String fieldId = "id";

	public Collection<Element> pull(JID jid);

	public PersistentElementBox push(Element element);

	public Map<String, Object> peek(Map<String, Object> query);

	public Map<String, Object> peek(Map<String, Object> query, Map<String, Object> update);
}
