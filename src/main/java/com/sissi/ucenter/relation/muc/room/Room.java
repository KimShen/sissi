package com.sissi.ucenter.relation.muc.room;

import com.sissi.context.JID;
import com.sissi.field.Field;
import com.sissi.field.Fields;

/**
 * MUC房间
 * 
 * @author kim 2014年2月20日
 */
public interface Room {

	public final static String CONFIG_HIDDEN_NATIVE = "HIDDEN_NATIVE";


	public boolean allowed(JID jid, RoomConfig key);

	public boolean allowed(JID jid, RoomConfig key, Object value);

	/**
	 * 获取在此房间中的保留昵称
	 * 
	 * @param jid
	 * @return
	 */
	public String reserved(JID jid);
	
	/**
	 * 销毁房间
	 * 
	 * @return
	 */
	public Room destory();

	/**
	 * 更新配置
	 * 
	 * @param fields
	 * @return
	 */
	public Room push(Fields fields);

	public Room push(Field<?> field);

	/**
	 * 获取配置
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T pull(String key, Class<T> clazz);
}
