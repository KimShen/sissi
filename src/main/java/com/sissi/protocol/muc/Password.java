package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.io.read.Metadata;

/**
 * @author kim 2014年2月22日
 */
@Metadata(uri = XMuc.XMLNS, localName = Password.NAME)
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement(name = Password.NAME)
public class Password {

	public final static String NAME = "password";

	private String text;

	public Password() {
		super();
	}

	public Password(String text) {
		super();
		this.text = text;
	}

	@XmlValue
	public String getText() {
		return this.text != null & !this.text.isEmpty() ? this.text : null;
	}

	public Password setText(String text) {
		this.text = text;
		return this;
	}
}
