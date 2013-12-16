package com.sissi.protocol.iq.register.form;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.data.XValue;

/**
 * @author kim 2013年12月16日
 */

@XmlRootElement(name = Instructions.NAME)
public class Instructions extends XValue {

	public final static String NAME = "instructions";

	public Instructions() {
		super();
	}

	public Instructions(String value) {
		super(value);
	}
}
