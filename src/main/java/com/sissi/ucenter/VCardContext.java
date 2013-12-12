package com.sissi.ucenter;

import com.sissi.context.JID;
import com.sissi.ucenter.field.Field.Fields;

/**
 * @author kim 2013年12月10日
 */
public interface VCardContext {

	public VCardContext set(JID jid, Fields fields);

	public <T extends Fields> T fill(JID jid, T fields);
}
