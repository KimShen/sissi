package com.sissi.protocol.offline;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Metadata;

/**
 * @author kim 2013-11-22
 */
@Metadata(uri = Delay.XMLNS, localName = Delay.NAME)
public class Delay {

	public final static String XMLNS = "urn:xmpp:delay";

	public final static String NAME = "delay";

	private String hit;

	private String from;

	private String stamp;

	public Delay() {
		super();
	}

	public Delay(String hit, String from, String stamp) {
		super();
		this.hit = hit;
		this.from = from;
		this.stamp = stamp;
	}

	@XmlValue
	public String getHit() {
		return this.hit;
	}

	public Delay setFrom(String from) {
		this.from = from;
		return this;
	}

	public Delay setStamp(String stamp) {
		this.stamp = stamp;
		return this;
	}

	@XmlAttribute
	public String getFrom() {
		return this.from;
	}

	@XmlAttribute
	public String getStamp() {
		return this.stamp;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
