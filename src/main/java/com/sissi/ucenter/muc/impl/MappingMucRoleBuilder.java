package com.sissi.ucenter.muc.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JID;
import com.sissi.ucenter.muc.MucRole;
import com.sissi.ucenter.muc.MucRoleBuilder;

/**
 * @author kim 2014年3月17日
 */
public class MappingMucRoleBuilder implements MucRoleBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final NothingMucChange nothing = new NothingMucChange();

	private final Map<String, MucRole> mapping = new HashMap<String, MucRole>();

	public MappingMucRoleBuilder(Set<MucRole> roles) {
		super();
		for (MucRole role : roles) {
			this.mapping.put(role.role(), role);
		}
	}

	@Override
	public MucRole build(String role) {
		MucRole change = this.mapping.get(role);
		return change != null ? change : this.nothing;
	}

	private final class NothingMucChange implements MucRole {

		@Override
		public MucRole change(JID group) {
			MappingMucRoleBuilder.this.log.warn("NothingMuc on " + group.asString());
			return null;
		}

		@Override
		public String role() {
			return null;
		}
	}
}
