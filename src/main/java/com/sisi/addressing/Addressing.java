package com.sisi.addressing;

import com.sisi.context.Context;
import com.sisi.context.JID;

/**
 * @author kim 2013-11-1
 */
public interface Addressing {

	public Context join(Context context);

	public Context find(JID jid);
	
	public Boolean isLogin(JID jid);
}
