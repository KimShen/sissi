package com.sissi.ucenter.relation;

import java.util.HashMap;
import java.util.Map;

import com.sissi.context.JID;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2013年12月30日
 */
public class MucWrapRelation implements RelationMuc {

	private final JID muc;
	
	private final String member;

	private String role;

	private String affiliation;

	public MucWrapRelation(JID member, JID muc) {
		super();
		this.muc = muc;
		this.member = member.asStringWithBare();
	}

	@Override
	public String getJID() {
		return this.muc.asStringWithBare();
	}

	@Override
	public String getName() {
		return this.muc.getResource();
	}

	@Override
	public String getRoom() {
		return this.muc.getUser();
	}

	@Override
	public String getRole() {
		return this.role;
	}

	@Override
	public String getMember() {
		return this.member;
	}

	@Override
	public String getService() {
		return this.muc.getHost();
	}

	@Override
	public String getAffiliation() {
		return this.affiliation;
	}

	@Override
	public String getSubscription() {
		return null;
	}

	@Override
	public MucWrapRelation set(String role, String affiliation) {
		this.role = role;
		this.affiliation = affiliation;
		return this;
	}

	@Override
	public Map<String, Object> plus() {
		Map<String, Object> plus = new HashMap<String, Object>();
		plus.put(KEY_ROOM, this.getRoom());
		plus.put(KEY_ROLE, this.getRole());
		plus.put(KEY_MEMBER, this.getMember());
		plus.put(KEY_SERVICE, this.getService());
		plus.put(KEY_AFFILICATION, this.getAffiliation());
		return plus;
	}
}
