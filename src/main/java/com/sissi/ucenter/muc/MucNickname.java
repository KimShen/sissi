package com.sissi.ucenter.muc;

import com.sissi.context.JID;

/**
 * @author kim 2014年3月5日
 */
public interface MucNickname {

	public JID exists(JID group, String nickname);
}
