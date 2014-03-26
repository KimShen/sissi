package com.sissi.protocol.iq.data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kim 2013年12月16日
 */
@XmlRootElement(name = Title.NAME)
public class Title extends XValue {

	public final static String NAME = "title";

	public Title() {
		super();
	}

	public Title(String value) {
		super(value);
	}
}
