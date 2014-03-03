package com.sissi.ucenter.muc.impl;

import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucWrapRelation implements RelationMuc {

	private final static Map<String, Object> plus = new HashMap<String, Object>();

	private final JID group;

	private final RelationMuc relation;

	private final Presence presence;

	public PresenceMucWrapRelation(JID group, Presence presence, RelationMuc relation) {
		this.group = group;
		this.relation = relation;
		this.presence = presence;
	}

	@Override
	public String getJID() {
		return this.group.asStringWithBare();
	}

	@Override
	public String getName() {
		return this.group.resource();
	}

	@Override
	public boolean isActivate() {
		return this.relation.isActivate();
	}

	@Override
	public String getRole() {
		return this.relation.getRole();
	}

	@Override
	public String getAffiliation() {
		return this.relation.getAffiliation();
	}

	@Override
	public Map<String, Object> plus() {
		XMuc xmuc = this.presence.findField(XMuc.NAME, XMuc.class);
		if (xmuc != null) {
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
