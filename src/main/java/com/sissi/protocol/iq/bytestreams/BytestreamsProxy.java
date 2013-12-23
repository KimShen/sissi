package com.sissi.protocol.iq.bytestreams;

/**
 * @author kim 2013年12月18日
 */
public class BytestreamsProxy {

	private String jid;

	private String name;

	private String host;

	private String port;

	public BytestreamsProxy(String jid, String name, String host, String port) {
		super();
		this.jid = jid;
		this.name = name;
		this.host = host;
		this.port = port;
	}

	public String getJid() {
		return jid;
	}

	public String getName() {
		return name;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}
}
