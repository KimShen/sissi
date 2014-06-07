package com.sissi.protocol.iq.search.field;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.field.FieldValue;

/**
 * @author kim 2014年6月6日
 */
@XmlRootElement(name = Instructions.NAME)
public class Instructions extends FieldValue {

	public final static String NAME = "instructions";

	public Instructions() {
		super();
	}

	public Instructions(String text) {
		super(text);
	}

	@Override
	public String getName() {
		return NAME.toUpperCase();
	}
}
