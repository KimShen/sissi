package com.sissi.ucenter.vcard;

import com.sissi.context.JID;
import com.sissi.field.Field;
import com.sissi.field.Fields;

/**
 * VCard
 * 
 * @author kim 2013年12月10日
 */
public interface VCardContext {
	
	public final static String FIELD_NICK = "NICK";

	public final static String FIELD_LOGOUT = "LOGOUT";
	
	public final static String FIELD_AVATOR = "AVATOR";

	public final static String FIELD_SIGNATURE = "SIGNATURE";

	/**
	 * 是否存在指定JID的VCard
	 * 
	 * @param jid
	 * @return
	 */
	public boolean exists(JID jid);

	/**
	 * 是否存在指定JID的VCard
	 * 
	 * @param jid
	 * @return
	 */
	public boolean exists(String jid);

	/**
	 * 更新VCard
	 * 
	 * @param jid
	 * @param fields
	 * @return
	 */
	public VCardContext push(JID jid, Fields fields);

	/**
	 * 更新VCard
	 * 
	 * @param jid
	 * @param field
	 * @return
	 */
	public VCardContext push(JID jid, Field<String> field);

	/**
	 * 获取VCard
	 * 
	 * @param jid
	 * @param name
	 * @return
	 */
	public Field<String> pull(JID jid, String name);

	public Field<String> pull(JID jid, String name, String def);

	/**
	 * 获取VCard
	 * 
	 * @param jid
	 * @param fields
	 * @return
	 */
	public <T extends Fields> T pull(JID jid, T fields);
}
