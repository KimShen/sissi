package com.sissi.ucenter.history;

import java.util.Collection;

import com.sissi.context.JID;
import com.sissi.protocol.Element;

/**
 * @author kim 2014年3月6日
 */
public interface HistoryRecover {

	public Collection<Element> pull(JID group, HistoryQuery query);
}
