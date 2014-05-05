package com.sissi.protocol.iq.si;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.iq.data.XData;

/**
 * @author kim 2013年12月13日
 */
@Metadata(uri = Feature.XMLNS, localName = Feature.NAME)
@XmlType(namespace = Feature.XMLNS)
@XmlRootElement
public class Feature implements Collector {

	public final static String XMLNS = "http://jabber.org/protocol/feature-neg";

	public final static String NAME = "feature";

	private XData x;

	public Feature setX(XData x) {
		this.x = x;
		return this;
	}

	@XmlElement
	public XData getX() {
		return this.x;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		this.x = XData.class.cast(ob);
	}
}
