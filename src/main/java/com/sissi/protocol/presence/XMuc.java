package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.read.MappingMetadata;
import com.sissi.ucenter.field.Field;

/**
 * @author kim 2013年12月30日
 */
/**
 * @author kim 2013年12月30日
 */
@MappingMetadata(uri = XMuc.XMLNS, localName = X.NAME)
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement
public class XMuc extends X implements Field<String> {

	public final static String XMLNS = "http://jabber.org/protocol/muc";

	@XmlAttribute
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
	public Boolean hasChild() {
		return false;
	}
}
