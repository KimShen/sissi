package com.sissi.protocol.iq.register.form;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.ucenter.field.impl.BeanField;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement(name = Required.NAME)
public class Required extends BeanField<String> {

	public final static Required FIELD = new Required();

	public final static String NAME = "required";

	private Required() {
	}
}
