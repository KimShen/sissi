package com.sissi.protocol.iq;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.Error;
import com.sissi.protocol.iq.bind.Bind;
import com.sissi.protocol.iq.register.Register;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.iq.session.Session;
import com.sissi.read.Collector;

/**
 * @author Kim.shen 2013-10-16
 */
@XmlRootElement(namespace = Stream.NAMESPACE)
public class IQ extends Protocol implements Collector {

	private final static List<Protocol> EMPTY_CHILDREN = new ArrayList<Protocol>();

	private List<Protocol> protocols;

	private Error error;

	public IQ() {
		super();
		super.setId(UUID.randomUUID().toString());
	}

	public IQ(Type type) {
		this();
		super.setType(type.toString());
	}

	@XmlElements({ @XmlElement(name = "bind", type = Bind.class), @XmlElement(name = "session", type = Session.class), @XmlElement(name = "query", type = Roster.class), @XmlElement(name = "query", type = Register.class) })
	public List<Protocol> getProtocols() {
		return protocols;
	}

	public IQ add(Protocol protocol) {
		if (this.protocols == null) {
			this.protocols = new ArrayList<Protocol>();
		}
		this.protocols.add(protocol.setParent(this));
		return this;
	}

	public List<Protocol> listChildren() {
		return this.protocols != null ? this.protocols : EMPTY_CHILDREN;
	}

	public IQ setError(Error error) {
		this.error = error;
		super.setType(Type.ERROR);
		return this;
	}

	@XmlElement
	public Error getError() {
		return error;
	}

	public IQ clear() {
		this.protocols = null;
		return this;
	}
	
	@Override
	public void set(String localName, Object ob) {
		this.add((Protocol.class.cast(ob)));
	}
}
