package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author kim 2013年12月30日
 */
abstract public class X extends Presence {

	public final static String NAME = "x";

	public String getName() {
		return NAME;
	}

	@XmlAttribute
	abstract public String getXmlns();
}
