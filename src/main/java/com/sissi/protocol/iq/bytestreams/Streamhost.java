package com.sissi.protocol.iq.bytestreams;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月18日
 */
@MappingMetadata(uri = Bytestreams.XMLNS, localName = Streamhost.NAME)
@XmlRootElement
public class Streamhost {

	public final static String NAME = "streamhost";

	private String jid;

	private String host;

	private String port;

	public Streamhost() {

	}

	public Streamhost(String jid, String host, String port) {
		super();
		this.jid = jid;
		this.host = host;
		this.port = port;
	}

	public Streamhost setJid(String jid) {
		this.jid = jid;
		return this;
	}

	public Streamhost setHost(String host) {
		this.host = host;
		return this;
	}

	public Streamhost setPort(String port) {
		this.port = port;
		return this;
	}

	@XmlAttribute
	public String getJid() {
		return jid;
	}

	@XmlAttribute
	public String getHost() {
		return host;
	}

	@XmlAttribute
	public String getPort() {
		return port;
	}
}
