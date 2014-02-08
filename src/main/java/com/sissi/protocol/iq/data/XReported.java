package com.sissi.protocol.iq.data;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.ucenter.field.Field;

/**
 * @author kim 2014年2月8日
 */
@XmlRootElement(name = XReported.NAME)
public class XReported extends XFieldWrap implements Field<Object>{

	public final static String NAME = "reported";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Object getValue() {
		return null;
	}
}
