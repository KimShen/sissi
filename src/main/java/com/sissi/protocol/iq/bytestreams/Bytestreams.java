package com.sissi.protocol.iq.bytestreams;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月18日
 */
@MappingMetadata(uri = Bytestreams.XMLNS, localName = Bytestreams.NAME)
@XmlType(namespace = Bytestreams.XMLNS)
@XmlRootElement(name = Bytestreams.NAME)
public class Bytestreams extends Protocol {

	public final static String XMLNS = "http://jabber.org/protocol/bytestreams";

	public final static String NAME = "query";

	private Streamhost streamhost;

	@XmlElement
	public Streamhost getStreamhost() {
		return streamhost;
	}

	public Bytestreams setStreamhost(Streamhost streamhost) {
		this.streamhost = streamhost;
		return this;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
