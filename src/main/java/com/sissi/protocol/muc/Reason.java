package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.io.read.Metadata;

/**
 * @author kim 2014年3月8日
 */
@Metadata(uri = { Owner.XMLNS, XUser.XMLNS, XMucAdmin.XMLNS }, localName = Reason.NAME)
@XmlRootElement(name = Reason.NAME)
public class Reason {

	public final static String NAME = "reason";

	private String text;

	public Reason() {
		super();
	}

	public Reason(String text) {
		super();
		this.text = text;
	}

	@XmlValue
	public String getText() {
		return this.text != null && !this.text.isEmpty() ? this.text : "";
	}

	public Reason setText(String text) {
		this.text = text;
		return this;
	}
}
