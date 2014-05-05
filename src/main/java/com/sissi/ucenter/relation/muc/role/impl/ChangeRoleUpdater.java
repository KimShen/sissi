package com.sissi.ucenter.relation.muc.role.impl;

import com.sissi.context.JID;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.relation.RelationContext;
import com.sissi.ucenter.relation.muc.MucRelationMapping;
import com.sissi.ucenter.relation.muc.role.RoleUpdater;

/**
 * 更新角色
 * 
 * @author kim 2014年3月17日
 */
public class ChangeRoleUpdater implements RoleUpdater {

	private final ItemRole role;

	private final MucRelationMapping mapping;

	private final RelationContext relationContext;

	public ChangeRoleUpdater(String role, MucRelationMapping mapping, RelationContext relationContext) {
		super();
		this.mapping = mapping;
		this.role = ItemRole.parse(role);
		this.relationContext = relationContext;
	}

	/*
	 * 更新MUC JID所有资源
	 * 
	 * @see com.sissi.ucenter.relation.muc.role.RoleUpdater#change(com.sissi.context.JID)
	 */
	@Override
	public RoleUpdater change(JID group) {
		for (JID each : this.mapping.mapping(group)) {
			this.relationContext.update(each, group, this.support());
		}
		return this;
	}

	@Override
	public String support() {
		return this.role.toString();
	}
}
