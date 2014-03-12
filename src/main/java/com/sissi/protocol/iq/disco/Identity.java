package com.sissi.protocol.iq.disco;

import javax.xml.bind.annotation.XmlAttribute;

import com.sissi.read.Metadata;

/**
 * @author kim 2013年12月20日
 */
@Metadata(uri = DiscoInfo.XMLNS, localName = Identity.NAME)
public class Identity extends DiscoFeature {

	public final static String NAME = "identity";

	private String category;

	private String type;

	private String name;

	public Identity() {
	}

	public Identity(String name, String type, String category) {
		this();
		this.category = category;
		this.type = type;
		this.name = name;
	}

	public Identity setCategory(String category) {
		this.category = category;
		return this;
	}

	public Identity setType(String type) {
		this.type = type;
		return this;
	}

	public Identity setName(String name) {
		this.name = name;
		return this;
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

	public Identity clone() {
		return new Identity(this.name, this.type, this.category);
	}
}
