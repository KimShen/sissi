package com.sisi.protocol.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sisi.protocol.Protocol;
import com.sisi.protocol.iq.Bind;
import com.sisi.protocol.iq.Session;
import com.sisi.read.Collector;

/**
 * @author Kim.shen 2013-10-16
 */
@XmlRootElement(namespace = "")
public class IQ extends Protocol implements Collector {

	private final static Set<String> EMPTY_QUERY = new HashSet<String>();

	private Map<String, Object> query;

	private List<Protocol> protocols;

	@XmlElements({ @XmlElement(name = "bind", type = Bind.class), @XmlElement(name = "session", type = Session.class) })
	public List<Protocol> getProtocols() {
		return protocols;
	}

	public void add(Protocol protocol) {
		if (this.protocols == null) {
			this.protocols = new ArrayList<Protocol>();
		}
		this.protocols.add(protocol);
	}

	@Override
	public void set(String localName, Object ob) {
		if (this.query == null) {
			this.query = new HashMap<String, Object>();
		}
		this.query.put(localName, ob);
	}

	public Set<String> listChildren() {
		return this.query != null ? this.query.keySet() : EMPTY_QUERY;
	}

	public Object findChild(String key) {
		return this.query == null ? null : this.query.get(key);
	}
}
