package com.sissi.protocol;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.sissi.protocol.feature.Required;

/**
 * @author kim 2013-10-24
 */
abstract public class Feature {

	private String xmlns;

	private Required required;

	public Feature(String xmlns) {
		this(xmlns, false);
	}

	public Feature(String xmlns, Boolean required) {
		super();
		this.xmlns = xmlns;
		this.required = required ? Required.REQUIRED : null;
	}

	@XmlAttribute
	public String getXmlns() {
		return this.xmlns;
	}

	@XmlElement
	public Required getRequired() {
		return this.required;
	}
}