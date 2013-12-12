package com.sissi.ucenter;

import java.util.List;

import com.sissi.context.JID;

/**
 * @author kim 2013年12月6日
 */
public interface BlockContext {

	public BlockContext block(JID from, JID to);
	
	public BlockContext unblock(JID from);
	
	public BlockContext unblock(JID from, JID to);

	public Boolean isBlock(JID from, JID to);

	public List<String> iBlockWho(JID from);
}
