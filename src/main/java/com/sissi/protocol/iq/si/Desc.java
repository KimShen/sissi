package com.sissi.protocol.iq.si;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.io.read.Metadata;

/**
 * @author kim 2013年12月31日
 */
@Metadata(uri = File.XMLNS, localName = Desc.NAME)
@XmlRootElement
public class Desc {

	public final static String NAME = "desc";

	private String text;

	@XmlValue
	public String getText() {
		return this.text;
	}

	public Desc setText(String text) {
		this.text = text;
		return this;
	}
}
