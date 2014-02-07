package com.sissi.protocol.iq.bytestreams;

/**
 * @author kim 2013年12月18日
 */
public class BytestreamsProxy {

	private final String jid;

	private final String name;

	private final String port;

	private final String domain;

	public BytestreamsProxy(String jid, String name, String domain, String port) {
		super();
		this.jid = jid;
		this.name = name;
		this.port = port;
		this.domain = domain;
	}

	public String getJid() {
		return this.jid;
	}

	public String getName() {
		return this.name;
	}

	public String getDomain() {
		return this.domain;
	}

	public String getPort() {
		return this.port;
	}
}
