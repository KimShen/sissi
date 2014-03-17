package com.sissi.ucenter.muc;

import com.sissi.context.JID;

/**
 * @author kim 2014年3月17日
 */
public interface MucRole {

	public MucRole change(JID group);

	public String role();
}
