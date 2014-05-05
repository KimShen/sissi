package com.sissi.protocol.iq.bytestreams;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.io.read.Metadata;

/**
 * @author kim 2013年12月18日
 */
@Metadata(uri = Bytestreams.XMLNS, localName = Streamhost.NAME)
@XmlRootElement
public class Streamhost {

	public final static String NAME = "streamhost";

	private String jid;

	private String host;

	private String port;

	public Streamhost() {

	}

	public Streamhost(BytestreamsProxy proxy) {
		super();
		this.host = proxy.getDomain();
		this.port = proxy.getPort();
		this.jid = proxy.getJid();
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
		return this.jid;
	}

	@XmlAttribute
	public String getHost() {
		return this.host;
	}

	@XmlAttribute
	public String getPort() {
		return this.port;
	}
}
