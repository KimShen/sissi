package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.MappingMetadata;
import com.sissi.ucenter.field.Field;

/**
 * @author kim 2013年12月13日
 */
@MappingMetadata(uri = XVCardPhoto.XMLNS, localName = XVCardPhoto.NAME)
@XmlRootElement(name = XVCardPhoto.NAME)
public class XVCardPhoto implements Field<String> {

	public final static String NAME = "photo";

	public final static String XMLNS = "vcard-temp:x:update";

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
}