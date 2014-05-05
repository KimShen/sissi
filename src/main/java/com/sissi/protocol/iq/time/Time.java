package com.sissi.protocol.iq.time;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2014年2月10日
 */
@Metadata(uri = Time.XMLNS, localName = Time.NAME)
@XmlType(namespace = Time.XMLNS)
@XmlRootElement(name = Time.NAME)
public class Time extends Protocol implements Collector {

	public final static String XMLNS = "urn:xmpp:time";

	public final static String NAME = "time";

	private TimeTzo tzo;

	private TimeUtc utc;

	@XmlElement
	public TimeTzo getTzo() {
		return this.tzo;
	}

	@XmlElement
	public TimeUtc getUtc() {
		return this.utc;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case TimeTzo.NAME:
			this.tzo = TimeTzo.class.cast(ob);
			return;
		case TimeUtc.NAME:
			this.utc = TimeUtc.class.cast(ob);
			return;
		}
	}
}
