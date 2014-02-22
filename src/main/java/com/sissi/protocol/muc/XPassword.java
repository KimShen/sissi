package com.sissi.protocol.muc;

import com.sissi.read.Metadata;

/**
 * @author kim 2014年2月22日
 */
@Metadata(uri = XMuc.XMLNS, localName = XPassword.NAME)
public class XPassword {

	public final static String NAME = "password";

	private String text;

	public String getText() {
		return text;
	}

	public XPassword setText(String text) {
		this.text = text;
		return this;
	}
}
