package com.sissi.ucenter.muc.impl;

import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.protocol.muc.XMuc;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucWrapRelation implements RelationMuc {

	private final static Map<String, Object> plus = new HashMap<String, Object>();

	private final XMuc xmuc;

	private final JID group;

	private final RelationMuc relation;

	// RelationMuc may be none
	public PresenceMucWrapRelation(JID group, XMuc xmuc, RelationMuc relation) {
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

	@Override
	public String affiliation() {
		return this.relation.affiliation();
	}

	public PresenceMucWrapRelation noneRole() {
		this.relation.noneRole();
		return this;
	}

	@Override
	public Map<String, Object> plus() {
		if (this.xmuc != null) {
			Map<String, Object> plus = new HashMap<String, Object>();
			plus.put("configs.password", xmuc.getPassword());
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
