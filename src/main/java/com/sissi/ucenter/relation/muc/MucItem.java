package com.sissi.ucenter.relation.muc;

import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年3月5日
 */
public interface MucItem {

	public String getJid();

	public String getRole();

	public String getAffiliation();

	/**
	 * 岗位受限
	 * 
	 * @return
	 */
	public boolean forbidden();

	/**
	 * whois
	 * 
	 * @param hidden
	 * @return
	 */
	public MucItem hidden(boolean hidden);

	public MucItem relation(MucRelation relation);

	public Presence presence();

	/**
	 * 根据Item生成对应Presence
	 * 
	 * @param affiliation MUC房间岗位限制,用于对比当前岗位
	 * @return
	 */
	public Presence presence(String affiliation);
}
