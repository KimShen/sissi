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
/**
 * @author kim 2014年3月6日
 */
@Metadata(uri = XMuc.XMLNS, localName = X.NAME)
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement
public class XMuc extends X implements Field<String>, Collector {

	public final static String XMLNS = "http://jabber.org/protocol/muc";

	private XPassword password;

	private XHistory history;

	@Override
	public String getXmlns() {
		return XMLNS;
	}

	public String getPassword() {
		return this.password != null ? this.password.getText() : null;
	}

	public boolean isHistory() {
		return this.history != null;
	}

	public XHistory history() {
		return this.history;
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
		switch (localName) {
		case XPassword.NAME:
			this.password = XPassword.class.cast(ob);
			return;
		case XHistory.NAME:
			this.history = XHistory.class.cast(ob);
			return;
		}
	}
}
