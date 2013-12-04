package com.sissi.protocol.iq.register.form;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.ucenter.RegisterContext.Field;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement(name = "required")
public class Required implements Field {

	public final static Required FIELD = new Required();

	private final static String NAME = "required";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getText() {
		return null;
	}
}
