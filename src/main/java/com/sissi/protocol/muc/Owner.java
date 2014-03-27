package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XData;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月24日
 */
@Metadata(uri = Owner.XMLNS, localName = Owner.NAME)
@XmlRootElement(name = Owner.NAME)
public class Owner extends Protocol implements Collector {

	public final static String NAME = "query";

	public final static String XMLNS = "http://jabber.org/protocol/muc#owner";

	private XData data;

	public Owner() {
		super();
	}

	public Owner(XData data) {
		super();
		this.data = data;
	}

	@XmlElement
	public XData getX() {
		return this.data;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		this.data = XData.class.cast(ob);
	}
}
