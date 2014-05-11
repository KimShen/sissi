package com.sissi.protocol.iq.data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kim 2013年12月16日
 */
@XmlRootElement(name = XTitle.NAME)
public class XTitle extends XValue {

	public final static String NAME = "title";

	public XTitle() {
		super();
	}

	public XTitle(String value) {
		super(value);
	}
}
