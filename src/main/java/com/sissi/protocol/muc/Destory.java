package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;

/**
 * @author kim 2014年3月30日
 */
@Metadata(uri = Owner.XMLNS, localName = Destory.NAME)
@XmlRootElement
public class Destory implements Collector {

	public final static String NAME = "destroy";

	private String jid;

	private Reason reason;

	public Destory setJid(String jid) {
		this.jid = jid;
		return this;
	}

	@XmlAttribute
	public String getJid() {
		return this.jid;
	}

	public Destory reason(String reason) {
		this.reason = reason != null ? new Reason(reason) : null;
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
