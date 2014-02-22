package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.presence.X;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2014年2月11日
 */
@Metadata(uri = XMuc.XMLNS, localName = X.NAME)
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement
public class XMuc extends X implements Field<String>, Collector {

	public final static String XMLNS = "http://jabber.org/protocol/muc";

	private XPassword password;

	@Override
	public String getXmlns() {
		return XMLNS;
	}

	public String getPassword() {
		return this.password != null ? this.password.getText() : null;
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
	public boolean hasChild() {
		return false;
	}

	@Override
	public void set(String localName, Object ob) {
		this.password = XPassword.class.cast(ob);
	}
}
