package com.sissi.protocol.iq.bind;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.read.Collector;
import com.sissi.read.MappingMetadata;

/**
 * @author Kim.shen 2013-10-20
 */
@MappingMetadata(uri = Bind.XMLNS, localName = Bind.NAME)
@XmlType(namespace = Bind.XMLNS)
@XmlRootElement
public class Bind extends Protocol implements Collector {

	public final static String XMLNS = "urn:ietf:params:xml:ns:xmpp-bind";

	public final static String NAME = "bind";

	private String jid;

	private Resource resource;

	public Bind() {
		super();
	}

	public Bind(String resource) {
		super();
		this.resource = new Resource(resource);
	}

	@XmlElement(name = "jid")
	public String getJid() {
		return jid;
	}

	public Bind setJid(String jid) {
		this.jid = jid;
		return this;
	}

	public Resource getResource() {
		return resource;
	}

	public Bind setResource(Resource resource) {
		this.resource = resource;
		return this;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public Boolean hasResource() {
		return this.resource != null && this.resource.hasResource();
	}

	public Bind clear() {
		super.clear();
		this.jid = null;
		this.resource = null;
		return this;
	}

	@Override
	public void set(String localName, Object ob) {
		this.setResource((Resource) ob);
	}
}
