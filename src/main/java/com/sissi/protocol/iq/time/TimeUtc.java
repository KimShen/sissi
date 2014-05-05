package com.sissi.protocol.iq.time;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.io.read.Metadata;

/**
 * @author kim 2014年2月10日
 */
@Metadata(uri = Time.XMLNS, localName = TimeUtc.NAME)
@XmlRootElement(name = TimeUtc.NAME)
public class TimeUtc {

	public final static String NAME = "utc";

	private String text;

	public TimeUtc() {
		super();
	}

	public TimeUtc setText(String text) {
		this.text = text;
		return this;
	}
	
	@XmlValue
	public String getText() {
		return text;
	}
}
