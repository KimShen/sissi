package com.sissi.addressing;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDs;

/**
 * @author kim 2014年1月15日
 */
public interface Addressing {

	public Addressing priority(JIDContext context);

	public Addressing join(JIDContext context);

	public Addressing leave(JIDContext context);

	public Addressing leave(JID jid);

	public Addressing leave(JID jid, Boolean usingResource);

	public JIDContext find(JID jid);

	public JIDContext find(JID jid, Boolean usingResource);

	public JIDContext findOne(JID jid);

	public JIDContext findOne(JID jid, Boolean usingResource);

	public JIDs resources(JID jid);

	public JIDs resources(JID jid, Boolean usingResource);
}
