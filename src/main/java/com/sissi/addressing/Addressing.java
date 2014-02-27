package com.sissi.addressing;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDs;

/**
 * @author kim 2014年1月15日
 */
public interface Addressing {

	public Addressing join(JIDContext context);

	public Addressing leave(JIDContext context);

	public Addressing priority(JIDContext context);

	public JIDContext find(JID jid);

	public JIDContext find(JID jid, boolean usingResource);

	public JIDContext findOne(JID jid);

	public JIDContext findOne(JID jid, boolean usingResource);

	public JIDContext findOne(JID jid, boolean usingResource, boolean offline);

	public JIDs resources(JID jid);

	public JIDs resources(JID jid, boolean usingResource);
}
