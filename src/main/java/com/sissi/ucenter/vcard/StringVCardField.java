package com.sissi.ucenter.vcard;

/**
 * @author kim 2013年12月11日
 */
public class StringVCardField extends GenericityVCardField<String> {
	
	public StringVCardField() {
		super(null, null);
	}

	public StringVCardField(String element, String value) {
		super(element, value);
	}
	
	public StringVCardField(Class<?> element, String value) {
		this(element.getSimpleName().toLowerCase(), value);
	}
}
