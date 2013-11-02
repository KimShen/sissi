package com.sissi.addressing;

import com.sissi.context.Context;
import com.sissi.context.JID;

/**
 * @author kim 2013-11-1
 */
public interface Addressing {

	public Context join(Context context);

	public Context find(JID jid);
	
	public Boolean isLogin(JID jid);
}
