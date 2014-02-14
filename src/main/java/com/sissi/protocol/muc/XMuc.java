package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.presence.X;
import com.sissi.read.Metadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2014年2月11日
 */
@Metadata(uri = XMuc.XMLNS, localName = X.NAME)
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement
public class XMuc extends X implements Field<String> {

	public final static String XMLNS = "http://jabber.org/protocol/muc";

	@Override
	public String getXmlns() {
		return XMLNS;
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
}
