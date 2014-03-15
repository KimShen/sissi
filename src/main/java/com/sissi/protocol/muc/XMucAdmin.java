package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月14日
 */
@Metadata(uri = XMucAdmin.XMLNS, localName = XMucAdmin.NAME)
@XmlType(namespace = XMucAdmin.XMLNS)
@XmlRootElement
public class XMucAdmin extends Protocol implements Collector {

	public final static String XMLNS = "http://jabber.org/protocol/muc#admin";

	public final static String NAME = "query";

	private XTarget item;

	public ItemRole role() {
		return ItemRole.parse(this.item.getRole());
	}

	public boolean item() {
		return this.getItem() != null && this.getItem().role();
	}

	@XmlElement
	public XTarget getItem() {
		return this.item;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		this.item = XTarget.class.cast(ob);
	}
}
