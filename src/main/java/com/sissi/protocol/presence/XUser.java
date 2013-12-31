package com.sissi.protocol.presence;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.ucenter.field.Field;

/**
 * @author kim 2013年12月30日
 */
@XmlRootElement
public class XUser extends X implements Field<String> {

	public final static String XMLNS = "http://jabber.org/protocol/muc#user";

	private XUserItem item;

	private List<XUserStatus> status;

	@Override
	public String getXmlns() {
		return XMLNS;
	}

	public XUser() {
		super();
	}

	public XUser(String jid, String role, String affiliation) {
		super();
		this.item = new XUserItem(jid, role, affiliation);
	}

	public XUser add(XUserStatus code) {
		if (this.status == null) {
			this.status = new ArrayList<XUserStatus>();
		}
		this.status.add(code);
		return this;
	}

	@XmlElements({ @XmlElement(name = XUserStatus.NAME, type = XUserStatus.class) })
	public List<XUserStatus> getStatus() {
		return status;
	}

	@XmlElement
	public XUserItem getItem() {
		return item;
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public Boolean hasChild() {
		return false;
	}
}
