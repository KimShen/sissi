package com.sissi.protocol.iq.disco;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.io.read.Metadata;

/**
 * @author kim 2013年12月5日
 */
@Metadata(uri = DiscoInfo.XMLNS, localName = DiscoFeature.NAME)
@XmlType(namespace = DiscoInfo.XMLNS)
@XmlRootElement(name = DiscoFeature.NAME)
public class DiscoFeature {

	public final static String NAME = "feature";

	private String var;

	public DiscoFeature() {
		super();
	}

	public DiscoFeature(String var) {
		super();
		this.var = var;
	}

	public DiscoFeature setVar(String var) {
		this.var = var;
		return this;
	}

	@XmlAttribute
	public String getVar() {
		return this.var;
	}
}
