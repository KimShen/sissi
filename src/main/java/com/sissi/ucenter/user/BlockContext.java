package com.sissi.ucenter.user;

import com.sissi.context.JID;
import com.sissi.context.JIDs;

/**
 * @author kim 2013年12月6日
 */
public interface BlockContext {

	public BlockContext block(JID from, JID to);
	
	public BlockContext unblock(JID from);
	
	public BlockContext unblock(JID from, JID to);

	public JIDs iBlockWho(JID jid);
	
	public boolean isBlock(JID from, JID to);
}
