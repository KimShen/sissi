package com.sissi.protocol.iq.disco.feature;

import javax.xml.bind.annotation.XmlAttribute;

import com.sissi.protocol.iq.disco.DiscoFeature;

/**
 * @author kim 2013年12月20日
 */
public class Identity extends DiscoFeature {

	public final static Identity FEATURE_MUC = new Identity("Macbeth Chat Service", "text", "conference");

	public final static Identity FEATURE_PROXY = new Identity("SOCKS5 Bytestreams Service", "bytestreams", "proxy");

	public final static String NAME = "identity";

	private String category;

	private String type;

	private String name;

	public Identity() {
		super(null);
	}

	public Identity(String name, String type, String category) {
		this();
		this.category = category;
		this.type = type;
		this.name = name;
	}

	@XmlAttribute
	public String getCategory() {
		return this.category;
	}

	@XmlAttribute
	public String getType() {
		return this.type;
	}

	@XmlAttribute
	public String getName() {
		return this.name;
	}
}
