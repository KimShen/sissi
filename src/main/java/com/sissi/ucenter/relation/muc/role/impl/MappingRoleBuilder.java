package com.sissi.ucenter.relation.muc.role.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JID;
import com.sissi.ucenter.relation.muc.role.RoleBuilder;
import com.sissi.ucenter.relation.muc.role.RoleUpdater;

/**
 * @author kim 2014年3月17日
 */
public class MappingRoleBuilder implements RoleBuilder {

	private final Log log = LogFactory.getLog(this.getClass());

	private final NothingRoleUpdater nothing = new NothingRoleUpdater();

	private final Map<String, RoleUpdater> mapping = new HashMap<String, RoleUpdater>();

	public MappingRoleBuilder(Set<RoleUpdater> roles) {
		super();
		for (RoleUpdater role : roles) {
			this.mapping.put(role.support(), role);
		}
	}

	@Override
	public RoleUpdater build(String role) {
		RoleUpdater change = this.mapping.get(role);
		return change != null ? change : this.nothing;
	}

	private final class NothingRoleUpdater implements RoleUpdater {

		@Override
		public RoleUpdater change(JID group) {
			MappingRoleBuilder.this.log.warn("Nothing on " + group.asString());
			return null;
		}

		@Override
		public String support() {
			return null;
		}
	}
}
