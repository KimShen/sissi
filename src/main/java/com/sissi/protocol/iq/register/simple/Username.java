package com.sissi.protocol.iq.register.simple;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.io.read.Metadata;
import com.sissi.protocol.iq.register.Register;

/**
 * @author kim 2013年12月3日
 */
@Metadata(uri = Register.XMLNS, localName = Username.NAME)
@XmlRootElement(name = Username.NAME)
public class Username extends ValueField {

	public final static Username FIELD = new Username();

	public final static String NAME = "username";

	@Override
	public String getName() {
		return NAME;
	}

	public boolean valid() {
		return this.getValue() != null & !this.getValue().isEmpty();
	}
}
