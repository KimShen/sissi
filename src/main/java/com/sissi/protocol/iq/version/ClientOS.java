package com.sissi.protocol.iq.version;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Metadata;

/**
 * @author kim 2014年2月20日
 */
@Metadata(uri = Client.XMLNS, localName = ClientOS.NAME)
@XmlRootElement(name = ClientOS.NAME)
public class ClientOS {

	public final static String NAME = "os";

	private String text;

	public ClientOS setText(String text) {
		this.text = text;
		return this;
	}

	@XmlValue
	public String getText() {
		return this.text;
	}
}
