package com.sissi.protocol.iq.data;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.field.Field;

/**
 * @author kim 2014年2月8日
 */
@XmlRootElement(name = XItem.NAME)
public class XItem extends XFieldWrap implements Field<Object> {

	public final static String NAME = "item";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Object getValue() {
		return null;
	}
}
