package com.sissi.ucenter.relation.muc.room;

/**
 * @author kim 2014年4月24日
 */
public enum RoomInfo {

	/**
	 * 联系人地址(Owners),jid-multi
	 */
	CONTACTJID,
	/**
	 * 房间描述
	 */
	DESCRIPTION, LANG,
	/**
	 * 日志下载路径
	 */
	LOGS,
	/**
	 * 房间在线人数
	 */
	OCCUPANTS,
	/**
	 * 房间主题
	 */
	SUBJECT,
	/**
	 * 是否允许修改主题
	 */
	SUBJECTMOD;

	private final static String prefix = "muc#roominfo_";

	public String toString() {
		return prefix + super.toString().toLowerCase();
	}
}
