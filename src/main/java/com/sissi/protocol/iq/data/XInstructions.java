package com.sissi.protocol.iq.data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kim 2013年12月16日
 */

@XmlRootElement(name = XInstructions.NAME)
public class XInstructions extends XValue {

	public final static String NAME = "instructions";

	public XInstructions() {
		super();
	}

	public XInstructions(String value) {
		super(value);
	}
}
