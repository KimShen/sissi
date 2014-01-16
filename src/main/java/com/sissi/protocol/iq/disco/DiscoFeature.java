package com.sissi.protocol.iq.disco;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author kim 2013年12月5日
 */
abstract public class DiscoFeature {

	public final static String NAME = "feature";

	private String var;

	public DiscoFeature(String var) {
		super();
		this.var = var;
	}

	@XmlAttribute
	public String getVar() {
		return this.var;
	}
}
