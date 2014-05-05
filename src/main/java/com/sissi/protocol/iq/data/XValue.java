package com.sissi.protocol.iq.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.io.read.Metadata;

/**
 * @author kim 2013年12月13日
 */
@Metadata(uri = XData.XMLNS, localName = XValue.NAME)
@XmlRootElement(name = XValue.NAME)
public class XValue implements Field<String> {

	public final static String NAME = "value";

	private String value;

	public XValue() {
		super();
	}

	public XValue(String value) {
		super();
		this.value = value;
	}

	public XValue setText(String text) {
		this.value = text;
		return this;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	@XmlValue
	public String getValue() {
		return this.value;
	}

	public boolean hasContent() {
		return this.value != null && !this.value.isEmpty();
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public boolean hasChild() {
		return false;
	}
}