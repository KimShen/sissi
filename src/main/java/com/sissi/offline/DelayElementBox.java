package com.sissi.offline;

import java.util.List;

import com.sissi.context.JID;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
public interface DelayElementBox {

	public DelayElementBox add(Element element);

	public List<Element> get(JID jid);
}
