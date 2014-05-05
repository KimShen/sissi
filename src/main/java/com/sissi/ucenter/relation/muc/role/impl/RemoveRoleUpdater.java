package com.sissi.ucenter.relation.muc.role.impl;

import com.sissi.context.JID;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.relation.RelationContext;
import com.sissi.ucenter.relation.muc.MucRelationMapping;
import com.sissi.ucenter.relation.muc.role.RoleUpdater;

/**
 * 删除角色
 * 
 * @author kim 2014年3月17日
 */
public class RemoveRoleUpdater implements RoleUpdater {

	private final MucRelationMapping mapping;

	private final RelationContext relationContext;

	public RemoveRoleUpdater(MucRelationMapping mapping, RelationContext relationContext) {
		super();
		this.mapping = mapping;
		this.relationContext = relationContext;
	}

	/*
	 * 删除MUC JID所有资源
	 * 
	 * @see com.sissi.ucenter.relation.muc.role.RoleUpdater#change(com.sissi.context.JID)
	 */
	@Override
	public RoleUpdater change(JID group) {
		for (JID each : this.mapping.mapping(group)) {
			this.relationContext.remove(each, group);
		}
		return this;
	}

	@Override
	public String support() {
		return ItemRole.NONE.toString();
	}
}
