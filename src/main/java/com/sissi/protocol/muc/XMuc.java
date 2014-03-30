package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.presence.X;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2014年3月6日
 */
@Metadata(uri = XMuc.XMLNS, localName = X.NAME)
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement
public class XMuc extends X implements Field<String>, Collector {

	public final static String XMLNS = "http://jabber.org/protocol/muc";

	private Password password;

	private History history;

	@Override
	public String getXmlns() {
		return XMLNS;
	}

	public String passowrd() {
		return this.password != null ? this.password.getText() : null;
	}

	public History history() {
		return this.history;
	}

	public boolean hasHistory() {
		return this.history != null;
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
		case Password.NAME:
			this.password = Password.class.cast(ob);
			return;
		case History.NAME:
			this.history = History.class.cast(ob);
			return;
		}
	}
}
