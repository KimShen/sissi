package com.sissi.protocol.iq.register.form;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.ucenter.vcard.StringVCardField;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement(name = "required")
public class Required extends StringVCardField {

	public final static Required FIELD = new Required();

	private final static String NAME = "required";

	private Required() {
		super(NAME, null);
	}
}
