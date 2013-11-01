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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.protocol.Protocol;
import com.sisi.protocol.iq.Bind;
import com.sisi.protocol.iq.Roster;
import com.sisi.protocol.iq.Session;
import com.sisi.read.Collector;

/**
 * @author Kim.shen 2013-10-16
 */
@XmlRootElement(namespace = "")
public class IQ extends Protocol implements Collector {

	private final static Log LOG = LogFactory.getLog(IQ.class);

	private final static Set<String> EMPTY_CHILDREN = new HashSet<String>();

	private Map<String, Object> children;

	private List<Protocol> protocols;

	@XmlElements({ @XmlElement(name = "bind", type = Bind.class), @XmlElement(name = "session", type = Session.class), @XmlElement(name = "query", type = Roster.class) })
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
		if (this.children == null) {
			this.children = new HashMap<String, Object>();
		}
		LOG.info("Set child: " + localName);
		this.children.put(localName, ob);
	}

	public Set<String> listChildren() {
		return this.children != null ? this.children.keySet() : EMPTY_CHILDREN;
	}

	public Object findChild(String key) {
		return this.children == null ? null : this.children.get(key);
	}
}
