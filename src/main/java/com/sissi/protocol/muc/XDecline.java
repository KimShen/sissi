package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月8日
 */
@Metadata(uri = XUser.XMLNS, localName = XDecline.NAME)
@XmlRootElement(name = XDecline.NAME)
public class XDecline implements Collector {

	public final static String NAME = "decline";

	private String to;

	private String from;

	private XReason reason;

	public XDecline setFrom(String from) {
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

	public XDecline setTo(String to) {
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
