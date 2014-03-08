package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月8日
 */
@Metadata(uri = XUser.XMLNS, localName = XInvite.NAME)
@XmlRootElement(name = XInvite.NAME)
public class XInvite implements Collector {

	public final static String NAME = "invite";

	private String to;

	private String from;

	private XReason reason;

	public XInvite setFrom(String from) {
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

	public XInvite setTo(String to) {
		this.to = to;
		return this;
	}

	@XmlElement
	public XReason getReason() {
		return this.reason;
	}

	@Override
	public void set(String localName, Object ob) {
		this.reason = XReason.class.cast(ob);
	}
}
