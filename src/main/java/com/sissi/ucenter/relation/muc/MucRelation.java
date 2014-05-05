package com.sissi.ucenter.relation.muc;

import com.sissi.ucenter.relation.Relation;

/**
 * MUC订阅关系
 * 
 * @author kim 2014年2月11日
 */
public interface MucRelation extends Relation {

	/**
	 * 指定角色
	 * 
	 * @param role
	 * @return
	 */
	public MucRelation role(String role);

	/**
	 * 强制指定角色
	 * 
	 * @param role
	 * @param force
	 * @return
	 */
	public MucRelation role(String role, boolean force);

	/**
	 * 指定岗位
	 * 
	 * @param affiliation
	 * @return
	 */
	public MucRelation affiliation(String affiliation);

	/**
	 * 强制指定岗位
	 * 
	 * @param affiliation
	 * @param force
	 * @return
	 */
	public MucRelation affiliation(String affiliation, boolean force);

	public String role();

	public String affiliation();

	public boolean outcast();

	/**
	 * 用户名比较
	 * 
	 * @param name
	 * @param allowNull
	 * @return
	 */
	public boolean name(String name, boolean allowNull);

	/**
	 * 强制Role = None
	 * 
	 * @return
	 */
	public MucRelation noneRole();
}
