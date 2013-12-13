package com.sissi.protocol.presence.x;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.vcard.ListVCardFields.Xmlns;

/**
 * @author kim 2013年12月13日
 */
@MappingMetadata(uri = "vcard-temp:x:update", localName = "photo")
@XmlRootElement(name = "photo")
public class XVCardPhoto implements Xmlns, Field<String> {

	public final static String NAME = XVCardPhoto.class.getSimpleName();

	private final static String XMLNS = "vcard-temp:x:update";

	private String text;

	public XVCardPhoto() {
		super();
	}

	public XVCardPhoto(String text) {
		super();
		this.text = text;
	}

	public XVCardPhoto setText(String text) {
		this.text = text;
		return this;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	@XmlValue
	public String getValue() {
		return this.text;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public Boolean hasChild() {
		return false;
	}

	@Override
	public String getXmlns() {
		return XMLNS;
	}
}