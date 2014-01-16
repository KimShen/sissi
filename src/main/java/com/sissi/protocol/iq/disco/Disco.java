package com.sissi.protocol.iq.disco;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年1月16日
 */
abstract class Disco extends Protocol {

	public final static String NAME = "query";

	private final String xmlns;

	private List<DiscoFeature> features;

	public Disco(String xmlns) {
		super();
		this.xmlns = xmlns;
	}

	public Disco add(DiscoFeature features) {
		if (this.features == null) {
			this.features = new ArrayList<DiscoFeature>();
		}
		this.features.add(features);
		return this;
	}

	public List<DiscoFeature> getDisco() {
		return this.features;
	}

	@XmlAttribute
	public String getXmlns() {
		return this.xmlns;
	}
}
