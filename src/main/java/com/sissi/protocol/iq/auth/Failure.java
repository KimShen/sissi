package com.sissi.protocol.iq.auth;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;

/**
 * @author Kim.shen 2013-10-19
 */
@XmlRootElement
public class Failure extends Protocol {

	public final static Failure INSTANCE = new Failure();

	private Failure() {

	}

	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}
}
