package com.sissi.ucenter.relation.muc.room;

/**
 * @author kim 2014年4月24日
 */
public enum RoomConfig {

	/**
	 * 允许查看真实JID的岗位,list-single
	 */
	WHOIS,
	/**
	 * 自定义,是否允许查看真实JID(是否为创建者或非匿名房间),boolean
	 */
	WHOISALLOW,
	/**
	 * 自定义,是否允许查看真实JID(是否为非匿名房间),boolean
	 */
	WHOISEXISTS,
	/**
	 * 自定义,是否已配置,boolean
	 */
	CONFIGED,
	/**
	 * 自定义,是否已激活(允许其他房客进入),boolean
	 */
	ACTIVATED,
	/**
	 * 是否允许邀请,boolean
	 */
	ALLOWINVITES,
	/**
	 * 是否允许注册,boolean
	 */
	ALLOWREGISTER,
	/**
	 * 自定义,岗位,text-single
	 */
	AFFILIATION,
	/**
	 * 自定义,是否符合岗位限制,boolean
	 */
	AFFILIATIONALLOW,
	/**
	 * 自定义,是否存在岗位限制,boolean
	 */
	AFFILIATIONEXISTS,
	/**
	 * 自定义,是否昵称锁定,boolean
	 */
	CHANGENICK,
	/**
	 * 是否允许更新主题,boolean
	 */
	CHANGESUBJECT,
	/**
	 * 是否开启日志,boolean
	 */
	ENABLELOGGING,
	/**
	 * 默认语言,text-single
	 */
	LANG, PUBSUB,
	/**
	 * 最大用户,list-single
	 */
	MAXUSERS,
	/**
	 * 是否为会员房间,boolean
	 */
	MEMBERSONLY,
	/**
	 * 是否为主持房间,boolean
	 */
	MODERATEDROOM,
	/**
	 * 是否为密码保护,boolean
	 */
	PASSWORDPROTECTEDROOM,
	/**
	 * 是否为持久房间,boolean
	 */
	PERSISTENTROOM,
	/**
	 * 允许出席广播的角色,list-multi
	 */
	PRESENCEBROADCAST,
	/**
	 * 是否为公开房间(允许搜索房间),boolean
	 */
	PUBLICROOM,
	/**
	 * Admin JID集合,jid-multi
	 */
	ROOMADMINS,
	/**
	 * 房间描述,text-single
	 */
	ROOMDESC,
	/**
	 * 房间名称,text-single
	 */
	ROOMNAME,
	/**
	 * Owner JID集合,jid-multi
	 */
	ROOMOWNERS,
	/**
	 * 房间密码,text-private
	 */
	ROOMSECRET;

	private final static String prefix = "muc#roomconfig_";

	public String toString() {
		return prefix + super.toString().toLowerCase();
	}
}
