package com.sissi.protocol.iq.version;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Metadata;

/**
 * @author kim 2014年2月20日
 */
@Metadata(uri = Client.XMLNS, localName = ClientName.NAME)
@XmlRootElement(name = ClientName.NAME)
public class ClientName {

	public final static String NAME = "name";

	private String text;

	public ClientName setText(String text) {
		this.text = text;
		return this;
	}

	@XmlValue
	public String getText() {
		return text;
	}
}
