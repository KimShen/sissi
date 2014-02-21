package com.sissi.ucenter;

/**
 * @author kim 2014年2月21日
 */
public interface MucStatusJudge {

	public final static String JUDEGE_JID = "jid";

	public final static String JUDEGE_HIDDEN = "hidden";

	public Object supply(String key);

	public boolean judge(String key, Object value);
}
