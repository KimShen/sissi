package com.sissi.ucenter.relation.muc.impl;

import java.util.HashMap;
import java.util.Map;

import com.sissi.config.Dictionary;
import com.sissi.context.JID;
import com.sissi.protocol.muc.XMuc;
import com.sissi.ucenter.relation.Relation;
import com.sissi.ucenter.relation.muc.MucRelation;

/**
 * @author kim 2014年2月11日
 */
public class PresenceWrapRelation implements MucRelation {

	private final static Map<String, Object> plus = new HashMap<String, Object>();

	private final XMuc xmuc;

	private final JID group;

	private final MucRelation relation;

	public PresenceWrapRelation(JID group, XMuc xmuc, MucRelation relation) {
		this.xmuc = xmuc;
		this.group = group;
		this.relation = relation;
	}

	@Override
	public String jid() {
		return this.group.asStringWithBare();
	}

	@Override
	public String name() {
		return this.group.resource();
	}

	public String resource() {
		return this.group.resource();
	}

	public boolean outcast() {
		return this.relation.outcast();
	}

	@Override
	public boolean activate() {
		return this.relation.activate();
	}

	public boolean name(String name, boolean allowNull) {
		return this.name().equals(name) || (this.name() == null && allowNull);
	}

	@Override
	public String role() {
		return this.relation.role();
	}

	public MucRelation role(String role) {
		return this.relation.role(role);
	}

	public MucRelation role(String role, boolean force) {
		return this.relation.role(role, force);
	}

	@Override
	public String affiliation() {
		return this.relation.affiliation();
	}

	public MucRelation affiliation(String affiliation) {
		return this.relation.affiliation(affiliation);
	}

	public MucRelation affiliation(String affiliation, boolean force) {
		return this.relation.affiliation(affiliation, force);
	}

	public PresenceWrapRelation noneRole() {
		this.relation.noneRole();
		return this;
	}

	@Override
	public Map<String, Object> plus() {
		if (this.xmuc != null) {
			Map<String, Object> plus = new HashMap<String, Object>();
			plus.put(Dictionary.FIELD_CONFIGS + "." + Dictionary.FIELD_PASSWORD, xmuc.passowrd());
			return plus;
		} else {
			return plus;
		}
	}

	@Override
	public <T extends Relation> T cast(Class<T> clazz) {
		return clazz.cast(this);
	}
}
