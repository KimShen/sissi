package com.sissi.ucenter.relation;

import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
public class PresenceMucWrapRelation implements RelationMuc {

	private final static Map<String, Object> plus = new HashMap<String, Object>();

	private final JID jid;

	private final RelationMuc muc;

	public PresenceMucWrapRelation(JID jid, RelationMuc muc) {
		this.jid = jid;
		this.muc = muc;
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
	public String getAffiliaion() {
		return this.muc.getAffiliaion();
	}

	@Override
	public Map<String, Object> plus() {
		return plus;
	}
}
