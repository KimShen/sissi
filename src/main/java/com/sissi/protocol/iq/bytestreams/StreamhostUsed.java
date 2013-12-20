package com.sissi.protocol.iq.bytestreams;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月19日
 */
@MappingMetadata(uri = Bytestreams.XMLNS, localName = StreamhostUsed.NAME)
@XmlRootElement(name = StreamhostUsed.NAME)
public class StreamhostUsed {

	public final static String NAME = "streamhost-used";

	private String jid;

	@XmlAttribute
	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}
}
