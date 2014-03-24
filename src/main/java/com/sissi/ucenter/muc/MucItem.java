package com.sissi.ucenter.muc;

import com.sissi.context.JID;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年3月5日
 */
public interface MucItem {

	public boolean refuse();

	public String getJid();

	public String getRole();

	public String getAffiliation();

	public MucItem hidden(boolean hidden);

	public MucItem relation(RelationMuc relation);

	public Presence presence(JID group, String affiliation);
}
