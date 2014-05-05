package com.sissi.ucenter.relation.muc.role;

import com.sissi.context.JID;

/**
 * 角色更新器
 * 
 * @author kim 2014年3月17日
 */
public interface RoleUpdater {

	/**
	 * 更新角色
	 * 
	 * @param group
	 * @return
	 */
	public RoleUpdater change(JID group);

	/**
	 * 支持类型
	 * 
	 * @return
	 */
	public String support();
}
