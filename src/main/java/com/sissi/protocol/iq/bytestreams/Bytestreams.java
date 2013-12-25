package com.sissi.protocol.iq.bytestreams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.read.Collector;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013年12月18日
 */
@MappingMetadata(uri = Bytestreams.XMLNS, localName = Bytestreams.NAME)
@XmlType(namespace = Bytestreams.XMLNS)
@XmlRootElement(name = Bytestreams.NAME)
public class Bytestreams extends Protocol implements Collector {

	public final static String XMLNS = "http://jabber.org/protocol/bytestreams";

	public final static String NAME = "query";

	private StreamhostUsed streamhostUsed;

	private List<Streamhost> streamhosts;

	private String sid;

	private String mode;

	private Activate activate;

	public Boolean isActivate() {
		return activate != null;
	}

	public Bytestreams setSid(String sid) {
		this.sid = sid;
		return this;
	}

	public Bytestreams setMode(String mode) {
		this.mode = mode;
		return this;
	}

	@XmlAttribute
	public String getMode() {
		return mode;
	}

	@XmlAttribute
	public String getSid() {
		return sid;
	}

	@XmlElements({ @XmlElement(name = Streamhost.NAME, type = Streamhost.class) })
	public List<Streamhost> getStreamhost() {
		return streamhosts;
	}

	@XmlElement(name = StreamhostUsed.NAME)
	public StreamhostUsed getStreamhostUsed() {
		return streamhostUsed;
	}

	public Bytestreams add(Streamhost streamhost) {
		if (this.streamhosts == null) {
			this.streamhosts = new ArrayList<Streamhost>();
		}
		this.streamhosts.add(streamhost);
		return this;
	}

	public Bytestreams sort(Comparator<Streamhost> comparator) {
		if (this.streamhosts != null) {
			Collections.sort(this.streamhosts, comparator);
		}
		return this;
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case Streamhost.NAME:
			this.add(Streamhost.class.cast(ob));
			return;
		case StreamhostUsed.NAME:
			this.streamhostUsed = StreamhostUsed.class.cast(ob);
			return;
		case Activate.NAME:
			this.activate = Activate.class.cast(ob);
			return;
		}
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public Boolean isUsed() {
		return this.streamhostUsed != null;
	}
}
