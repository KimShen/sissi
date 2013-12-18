package com.sissi.protocol.starttls;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月18日
 */
@XmlType(namespace = Starttls.XMLNS)
@XmlRootElement
public class Failure extends Protocol {

	public final static Failure FAILURE = new Failure();

	private Failure() {

	}

	@XmlAttribute
	public String getXmlns() {
		return Starttls.XMLNS;
	}
}
