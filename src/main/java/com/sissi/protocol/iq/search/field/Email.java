package com.sissi.protocol.iq.search.field;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.field.FieldValue;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.iq.search.Search;

/**
 * @author kim 2014年6月6日
 */
@Metadata(uri = Search.XMLNS, localName = Email.NAME)
@XmlRootElement(name = Email.NAME)
public class Email extends FieldValue {

	public final static Email FIELD = new Email();

	public final static String NAME = "email";

	public Email() {
		super();
	}

	public Email(String text) {
		super(text);
	}

	@Override
	public String getName() {
		return NAME.toUpperCase();
	}
}
