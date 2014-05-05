package com.sissi.ucenter.relation.muc.status;

import java.util.Set;

import com.sissi.ucenter.relation.muc.MucItem;

/**
 * MUC状态码
 * 
 * @author kim 2014年3月5日
 */
public interface CodeStatus {

	/**
	 * MUC JID
	 * 
	 * @return
	 */
	public String group();

	/**
	 * 回路,JID与宿主相同
	 * 
	 * @return
	 */
	public boolean loop();

	public boolean hidden();

	/**
	 * 是否已包含指定状态码
	 * 
	 * @param code
	 * @return
	 */
	public boolean contain(String code);

	public MucItem getItem();

	/**
	 * 重置状态码
	 * 
	 * @return
	 */
	public CodeStatus clear();

	public CodeStatus add(String code);

	public CodeStatus add(Set<String> codes);

	public <T extends CodeStatus> T cast(Class<T> clazz);
}
