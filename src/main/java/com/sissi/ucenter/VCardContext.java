package com.sissi.ucenter;

import com.sissi.context.JID;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2013年12月10日
 */
public interface VCardContext {
	
	public Boolean exists(JID jid);

	public VCardContext set(JID jid, Fields fields);

	public <T extends Fields> T get(JID jid, T fields);
}
