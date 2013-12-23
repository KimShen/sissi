package com.sissi.protocol.iq.register.simple;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.register.Register;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013年12月3日
 */
@MappingMetadata(uri = Register.XMLNS, localName = Password.NAME)
@XmlRootElement(name = Password.NAME)
public class Password extends QuickField {

	public final static Password FIELD = new Password();

	public final static String NAME = "password";

	@Override
	public String getName() {
		return NAME;
	}
}
