package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月8日
 */
@Metadata(uri = { XUser.XMLNS, XMucAdmin.XMLNS }, localName = XReason.NAME)
@XmlRootElement(name = XReason.NAME)
public class XReason {

	public final static String NAME = "reason";

	private String text;

	public XReason() {
		super();
	}

	public XReason(String text) {
		super();
		this.text = text;
	}

	@XmlValue
	public String getText() {
		return this.text != null & !this.text.isEmpty() ? this.text : null;
	}

	public XReason setText(String text) {
		this.text = text;
		return this;
	}
}
