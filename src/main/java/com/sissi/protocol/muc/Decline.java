package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;

/**
 * @author kim 2014年3月8日
 */
@Metadata(uri = XUser.XMLNS, localName = Decline.NAME)
@XmlRootElement(name = Decline.NAME)
public class Decline implements Collector {

	public final static String NAME = "decline";

	private String to;

	private String from;

	private Reason reason;

	public Decline setFrom(String from) {
		this.from = from;
		this.to = null;
		return this;
	}

	@XmlAttribute
	public String getFrom() {
		return this.from;
	}

	@XmlAttribute
	public String getTo() {
		return this.to;
	}

	public Decline setTo(String to) {
		this.to = to;
		return this;
	}

	@XmlElement
	public Reason getReason() {
		return this.reason;
	}

	@Override
	public void set(String localName, Object ob) {
		this.reason = Reason.class.cast(ob);
	}
}
