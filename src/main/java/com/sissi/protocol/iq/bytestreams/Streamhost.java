package com.sissi.protocol.iq.bytestreams;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kim 2013年12月18日
 */
@XmlRootElement
public class Streamhost {

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
