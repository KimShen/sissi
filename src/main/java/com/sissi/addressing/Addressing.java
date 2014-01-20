package com.sissi.addressing;

import java.util.List;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;

/**
 * @author kim 2014年1月15日
 */
public interface Addressing {

	public Addressing join(JIDContext context);

	public Addressing leave(JID jid);
	
	public Addressing leave(JID jid, Boolean usingResource);

	public Addressing leave(JIDContext context);

	public Addressing activate(JIDContext context);

	public Addressing priority(JIDContext context);

	public JIDContext find(JID jid);

	public JIDContext find(JID jid, Boolean usingResource);

	public JIDContext findOne(JID jid);

	public JIDContext findOne(JID jid, Boolean usingResource);

	public Integer others(JID jid);

	public Integer others(JID jid, Boolean usingResource);
	
	public List<String> resources(JID jid);
}
