package com.sissi.write;

/**
 * @author kim 2013年12月23日
 */
public interface TransferParam {

	public final static String KEY_CONTEXT = "CONTEXT";

	public final static String KEY_JID = "JID";

	public final static String KEY_SI = "SI";

	public final static String KEY_FROM = "from";

	public final static String KEY_TO = "to";

	public <T> T find(String key, Class<T> clazz);
}
