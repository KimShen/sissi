package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.context.JID;
import com.sissi.ucenter.MucGroupConfig;
import com.sissi.ucenter.MucGroupContext;
import com.sissi.ucenter.MucStatusJudge;
import com.sissi.ucenter.RelationMuc;

/**
 * @author kim 2014年2月11日
 */
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement(name = Item.NAME)
public class Item implements MucStatusJudge {

	public final static String NAME = "item";

	private MucGroupConfig config;

	private String affiliation;

	private String role;

	private String jid;

	private JID group;

	private JID current;

	public Item() {
	}
	
	public Item(JID group, JID current, RelationMuc muc, MucGroupContext mucGroupContext) {
		this(group, current, (String)null, muc, mucGroupContext);
	}

	public Item(JID group, JID current, JID jid, RelationMuc muc, MucGroupContext mucGroupContext) {
		this(group, current, jid.asString(), muc, mucGroupContext);
	}

	public Item(JID group, JID current, String jid, RelationMuc muc, MucGroupContext mucGroupContext) {
		super();
		this.jid = jid;
		this.group = group;
		this.current = current;
		this.role = muc.getRole();
		this.affiliation = muc.getAffiliaion();
		this.config = mucGroupContext.find(this.group);
	}

	private boolean hidden() {
		return this.config.allowed(MucGroupConfig.HIDDEN, this.current);
	}

	public boolean equals(String jid) {
		return this.jid.equals(jid);
	}

	public boolean jid(String jid) {
		return this.jid != null && this.jid.equals(jid) ? true : false;
	}

	@XmlAttribute
	public String getJid() {
		return this.hidden() ? null : this.jid;
	}

	@XmlAttribute
	public String getAffiliation() {
		return this.affiliation;
	}

	@XmlAttribute
	public String getRole() {
		return ItemRole.NONE.equals(this.role) ? this.config.mapping(this.getAffiliation()) : this.role;
	}

	public Object supply(String key) {
		switch (key) {
		case MucStatusJudge.JUDEGE_JID:
			return this.jid;
		}
		return null;
	}

	public boolean judge(String key, Object value) {
		switch (key) {
		case MucStatusJudge.JUDEGE_HIDDEN:
			return this.hidden();
		}
		return false;
	}
}
