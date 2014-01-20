package com.sissi.offline;

import java.util.Collection;

import com.sissi.context.JID;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
public interface DelayElementBox {

	public DelayElementBox push(Element element);

	public Collection<Element> pull(JID jid);
}
