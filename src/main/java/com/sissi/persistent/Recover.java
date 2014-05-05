package com.sissi.persistent;

import java.util.Collection;

import com.sissi.context.JID;
import com.sissi.protocol.Element;

/**
 * MUC历史消息服务
 * 
 * @author kim 2014年3月6日
 */
public interface Recover {

	public Collection<Element> pull(JID jid, RecoverQuery query);
}
