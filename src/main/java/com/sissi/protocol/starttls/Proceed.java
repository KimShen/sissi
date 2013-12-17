package com.sissi.protocol.starttls;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月17日
 */
@XmlRootElement
public class Proceed extends Protocol {

	public final static Proceed PROCEED = new Proceed();
	
	private Proceed() {

	}

	@XmlAttribute
	public String getXmlns() {
		return Starttls.XMLNS;
	}
}
