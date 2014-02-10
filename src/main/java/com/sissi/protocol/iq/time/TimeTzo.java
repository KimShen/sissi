package com.sissi.protocol.iq.time;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Metadata;

/**
 * @author kim 2014年2月10日
 */
@Metadata(uri = Time.XMLNS, localName = TimeTzo.NAME)
@XmlRootElement(name = TimeTzo.NAME)
public class TimeTzo {

	public final static String NAME = "tzo";

	private String text;

	public TimeTzo() {
		super();
	}

	public TimeTzo setText(String text) {
		this.text = text;
		return this;
	}

	@XmlValue
	public String getText() {
		return text;
	}
}
