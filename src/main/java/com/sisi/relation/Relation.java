package com.sisi.relation;

import java.util.List;

import com.sisi.context.JID;

/**
 * @author kim 2013-11-1
 */
public interface Relation {

	public List<JID> relation(JID jid);

	public List<JID> relation(JID from, JID to);
}
