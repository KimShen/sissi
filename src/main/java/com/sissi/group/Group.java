package com.sissi.group;

import com.sissi.context.Context;
import com.sissi.context.JID;

/**
 * @author kim 2013-10-30
 */
public interface Group extends Broadcast {
	
	public JID jid();

	public void add(Context context);

	public void remove(Context context);
}
