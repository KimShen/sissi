package com.sissi.protocol.iq;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bind.Bind;
import com.sissi.protocol.iq.block.BlockList;
import com.sissi.protocol.iq.block.Blocked;
import com.sissi.protocol.iq.block.UnBlock;
import com.sissi.protocol.iq.disco.Info;
import com.sissi.protocol.iq.register.Register;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.iq.session.Session;
import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.read.Collector;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author Kim.shen 2013-10-16
 */
@MappingMetadata(uri = IQ.XMLNS, localName = IQ.NAME)
@XmlRootElement
public class IQ extends Protocol implements Collector {

	public final static String XMLNS = "jabber:client";

	public final static String NAME = "iq";

	private final static List<Protocol> EMPTY_CHILDREN = new ArrayList<Protocol>();

	private List<Protocol> protocols;

	@XmlElements({ @XmlElement(name = "vCard", type = VCard.class), @XmlElement(name = "bind", type = Bind.class), @XmlElement(name = "session", type = Session.class), @XmlElement(name = "query", type = Roster.class), @XmlElement(name = "query", type = Register.class), @XmlElement(name = "query", type = Info.class), @XmlElement(name = "block", type = Blocked.class), @XmlElement(name = "unblock", type = UnBlock.class), @XmlElement(name = "blocklist", type = BlockList.class) })
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

	public IQ close() {
		this.protocols = null;
		return this;
	}

	@Override
	public void set(String localName, Object ob) {
		this.add(Protocol.class.cast(ob));
	}
}
