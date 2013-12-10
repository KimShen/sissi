package com.sissi.addressing;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;

/**
 * @author kim 2013-11-1
 */
public interface Addressing {

	public Addressing ban(JIDContext context);

	public Addressing join(JIDContext context);

	public Addressing leave(JIDContext context);

	public JIDContext find(JID jid);

	public JIDContext findOne(JID jid);
}
