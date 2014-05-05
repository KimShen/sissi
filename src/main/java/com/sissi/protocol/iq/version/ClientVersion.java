package com.sissi.protocol.iq.version;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.io.read.Metadata;

/**
 * @author kim 2014年2月20日
 */
@Metadata(uri = Client.XMLNS, localName = ClientVersion.NAME)
@XmlRootElement(name = ClientVersion.NAME)
public class ClientVersion {

	public final static String NAME = "version";

	private String text;

	public ClientVersion setText(String text) {
		this.text = text;
		return this;
	}

	@XmlValue
	public String getText() {
		return this.text;
	}
}
