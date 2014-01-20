package com.sissi.addressing;

import java.util.Collection;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;

/**
 * @author kim 2014年1月15日
 */
public interface Addressing {

	public Addressing join(JIDContext context);

	public Addressing leave(JID jid);
	
	public Addressing leave(JIDContext context);
	
	public Addressing leave(JID jid, Boolean usingResource);

	public Addressing priority(JIDContext context);

	public JIDContext find(JID jid);

	public JIDContext find(JID jid, Boolean usingResource);

	public JIDContext findOne(JID jid);

	public JIDContext findOne(JID jid, Boolean usingResource);

	public Integer others(JID jid);

	public Integer others(JID jid, Boolean usingResource);
	
	public Collection<String> resources(JID jid);
}
