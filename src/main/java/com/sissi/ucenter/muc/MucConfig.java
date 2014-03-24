package com.sissi.ucenter.muc;

import com.sissi.context.JID;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2014年2月20日
 */
public interface MucConfig {

	public final static String NICK = "NICK";

	public final static String COUNT = "COUNT";

	public final static String INVITE = "INVITE";

	public final static String SUBJECT = "SUBJECT";

	public final static String REGISTER = "REGISTER";

	public final static String PASSWORD = "PASSWORD";

	public final static String ACTIVATE = "ACTIVATE";

	public final static String AFFILIATION_EXISTS = "AFFILIATION_EXISTS";

	public final static String AFFILIATION_CHECK = "AFFILIATION_CHECK";

	public final static String HIDDEN_NATIVE = "HIDDEN_NATIVE";

	public final static String HIDDEN_COMPUTER = "HIDDEN_COMPUTE";

	public boolean allowed(JID jid, String key, Object value);

	public String mapping(String affiliation);

	public MucConfig push(Fields fields);

	public MucConfig push(Field<?> field);

	public <T> T pull(String key, Class<T> clazz);
}
