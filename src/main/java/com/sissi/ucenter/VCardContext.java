package com.sissi.ucenter;

import com.sissi.context.JID;
import com.sissi.ucenter.field.Field.Fields;

/**
 * @author kim 2013年12月10日
 */
public interface VCardContext {

	public VCardContext push(JID jid, Fields fields);

	public <T extends Fields> T pull(JID jid, T fields);
}
