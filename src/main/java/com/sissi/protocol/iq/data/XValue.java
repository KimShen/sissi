package com.sissi.protocol.iq.data;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.field.FieldValue;
import com.sissi.io.read.Metadata;

/**
 * @author kim 2013年12月13日
 */
@Metadata(uri = XData.XMLNS, localName = XValue.NAME)
@XmlRootElement(name = XValue.NAME)
public class XValue extends FieldValue {

	public final static String NAME = "value";

	public XValue() {
		super();
	}

	public XValue(String value) {
		super(value);
	}

	@Override
	public String getName() {
		return NAME;
	}

	public boolean content() {
		return super.getValue() != null && !super.getValue().isEmpty();
	}
}