package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Metadata;

/**
 * @author kim 2014年2月22日
 */
@Metadata(uri = XMuc.XMLNS, localName = XPassword.NAME)
@XmlRootElement(name = XPassword.NAME)
public class XPassword {

	public final static String NAME = "password";

	private String text;

	public XPassword() {
		super();
	}

	public XPassword(String text) {
		super();
		this.text = text;
	}

	@XmlValue
	public String getText() {
		return this.text != null & !this.text.isEmpty() ? this.text : null;
	}

	public XPassword setText(String text) {
		this.text = text;
		return this;
	}
}
