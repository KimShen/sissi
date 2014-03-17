package com.sissi.ucenter.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDs;

/**
 * @author kim 2014年3月6日
 */
public interface MucJIDs extends JIDs {

	public JID jid();

	public boolean same(JID jid);

	public boolean like(JID jid);
}
