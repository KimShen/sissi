package com.sissi.ucenter.user;

import com.sissi.context.JID;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2013年12月10日
 */
public interface VCardContext {

	public final static String FIELD_NICKNAME = "NICKNAME";

	public final static String FIELD_LOGOUT = "LOGOUT";

	public final static String FIELD_SIGNATURE = "SIGNATURE";

	public boolean exists(String jid);

	public boolean exists(JID jid);

	public VCardContext set(JID jid, Fields fields);

	public VCardContext set(JID jid, Field<String> field);

	public <T extends Fields> T get(JID jid, T fields);

	public Field<String> get(JID jid, String name);
}
