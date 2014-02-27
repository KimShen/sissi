package com.sissi.ucenter.relation.muc;

import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.Relation;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucWrapRelation implements RelationMuc {

	private final static Map<String, Object> plus = new HashMap<String, Object>();

	private final JID jid;

	private final RelationMuc muc;

	private final Presence presence;

	public PresenceMucWrapRelation(JID jid, Presence presence, RelationMuc muc) {
		this.jid = jid;
		this.muc = muc;
		this.presence = presence;
	}

	@Override
	public String getJID() {
		return this.jid.asStringWithBare();
	}

	@Override
	public String getName() {
		return this.jid.resource();
	}

	@Override
	public boolean isActivate() {
		return this.muc.isActivate();
	}

	@Override
	public String getRole() {
		return this.muc.getRole();
	}

	@Override
	public String getAffiliation() {
		return this.muc.getAffiliation();
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
