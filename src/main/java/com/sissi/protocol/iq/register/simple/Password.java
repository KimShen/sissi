package com.sissi.protocol.iq.register.simple;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.field.FieldValue;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.iq.register.Register;

/**
 * @author kim 2013年12月3日
 */
@Metadata(uri = Register.XMLNS, localName = Password.NAME)
@XmlRootElement(name = Password.NAME)
public class Password extends FieldValue {

	public final static Password FIELD = new Password();

	public final static String NAME = "password";

	@Override
	public String getName() {
		return NAME;
	}
}
