package com.sissi.addressing;

import com.sissi.context.JIDContext;
import com.sissi.context.JID;

/**
 * @author kim 2013-11-1
 */
public interface Addressing {

	public JIDContext join(JIDContext context);

	public JIDContext find(JID jid);
	
	public Boolean isOnline(JID jid);
}
