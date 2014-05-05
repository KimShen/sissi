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

import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月18日
 */
@Metadata(uri = Bytestreams.XMLNS, localName = Bytestreams.NAME)
@XmlType(namespace = Bytestreams.XMLNS)
@XmlRootElement(name = Bytestreams.NAME)
public class Bytestreams extends Protocol implements Collector {

	public final static String XMLNS = "http://jabber.org/protocol/bytestreams";

	public final static String NAME = "query";

	private StreamhostUsed streamhostUsed;

	private StreamhostActivate activate;

	private List<Streamhost> streamhosts;

	private String sid;

	private String mode;

	public boolean isUsed() {
		return this.streamhostUsed != null;
	}

	public boolean isActivate() {
		return this.activate != null;
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
		return this.mode;
	}

	@XmlAttribute
	public String getSid() {
		return this.sid;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@XmlElements({ @XmlElement(name = Streamhost.NAME, type = Streamhost.class) })
	public List<Streamhost> getStreamhost() {
		return this.streamhosts;
	}

	@XmlElement(name = StreamhostUsed.NAME)
	public StreamhostUsed getStreamhostUsed() {
		return this.streamhostUsed;
	}

	public Bytestreams setStreamhostUsed(StreamhostUsed streamhostUsed) {
		this.streamhostUsed = streamhostUsed;
		this.streamhosts = null;
		return this;
	}

	public Bytestreams add(Streamhost streamhost, boolean clear) {
		if (clear) {
			this.streamhosts = null;
		}
		return this.add(streamhost);
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
		case StreamhostActivate.NAME:
			this.activate = StreamhostActivate.class.cast(ob);
			return;
		}
	}
}
