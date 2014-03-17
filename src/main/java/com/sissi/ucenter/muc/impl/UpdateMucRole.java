package com.sissi.ucenter.muc.impl;

import com.sissi.context.JID;
import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.RelationContext;
import com.sissi.ucenter.muc.MucRole;
import com.sissi.ucenter.muc.RelationMucMapping;

/**
 * @author kim 2014年3月17日
 */
public class UpdateMucRole implements MucRole {

	private final ItemRole role;

	private final RelationMucMapping mapping;

	private final RelationContext relationContext;

	public UpdateMucRole(String role, RelationMucMapping mapping, RelationContext relationContext) {
		super();
		this.mapping = mapping;
		this.role = ItemRole.parse(role);
		this.relationContext = relationContext;
	}

	@Override
	public MucRole change(JID group) {
		for (JID each : this.mapping.mapping(group)) {
			this.relationContext.update(each, group, this.role());
		}
		return this;
	}

	@Override
	public String role() {
		return this.role.toString();
	}
}
