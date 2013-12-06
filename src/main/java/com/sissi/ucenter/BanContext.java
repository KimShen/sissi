package com.sissi.ucenter;

import java.util.List;

import com.sissi.context.JID;

/**
 * @author kim 2013年12月6日
 */
public interface BanContext {

	public void ban(JID from, JID to);

	public void free(JID from, JID to);

	public Boolean isBan(JID from, JID to);

	public List<String> iBanedWho(JID from);
}
