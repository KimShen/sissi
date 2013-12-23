package com.sissi.protocol.iq.register.simple;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.register.Register;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013年12月3日
 */
@MappingMetadata(uri = Register.XMLNS, localName = Username.NAME)
@XmlRootElement(name = Username.NAME)
public class Username extends QuickField {

	public final static Username FIELD = new Username();

	public final static String NAME = "username";

	@Override
	public String getName() {
		return NAME;
	}
}
