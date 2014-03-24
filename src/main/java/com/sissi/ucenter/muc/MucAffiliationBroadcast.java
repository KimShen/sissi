package com.sissi.ucenter.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;

/**
 * @author kim 2014年3月24日
 */
public interface MucAffiliationBroadcast {

	public MucAffiliationBroadcast broadcast(JID jid, JID group, JIDContext invoker, MucItem item, MucConfig config);
}
