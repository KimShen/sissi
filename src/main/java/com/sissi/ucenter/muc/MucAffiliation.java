package com.sissi.ucenter.muc;

import com.sissi.context.JID;

/**
 * @author kim 2014年3月9日
 */
public interface MucAffiliation {

	public MucAffiliation reject(JID jid);

	public MucAffiliation approve(JID jid, String affiliation);
}
