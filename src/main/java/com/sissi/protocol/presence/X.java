package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月11日
 */
@XmlRootElement
public class X extends Protocol {

	private final static String XMLNS = "vcard-temp:x:update";

	private String photo;

	public X() {
		super();
	}

	public X(String photo) {
		super();
		this.photo = photo;
	}

	@XmlElement
	public String getPhoto() {
		return photo;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
