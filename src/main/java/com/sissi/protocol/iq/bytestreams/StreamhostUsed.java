package com.sissi.protocol.iq.bytestreams;

import javax.xml.bind.annotation.XmlAttribute;

import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013年12月19日
 */
@MappingMetadata(uri = Bytestreams.XMLNS, localName = StreamhostUsed.NAME)
public class StreamhostUsed {

	public final static String NAME = "streamhost-used";

	private String jid;

	@XmlAttribute
	public String getJid() {
		return jid;
	}

	public StreamhostUsed setJid(String jid) {
		this.jid = jid;
		return this;
	}
}
