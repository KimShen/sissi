package com.sissi.protocol.iq;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.iq.bind.Bind;
import com.sissi.protocol.iq.block.BlockList;
import com.sissi.protocol.iq.block.Blocked;
import com.sissi.protocol.iq.block.UnBlock;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.disco.DiscoInfo;
import com.sissi.protocol.iq.disco.DiscoItems;
import com.sissi.protocol.iq.ping.Ping;
import com.sissi.protocol.iq.register.Register;
import com.sissi.protocol.iq.roster.Roster;
import com.sissi.protocol.iq.session.Session;
import com.sissi.protocol.iq.si.Si;
import com.sissi.protocol.iq.vcard.VCard;
import com.sissi.read.Collector;
import com.sissi.read.MappingMetadata;

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
	
	public String getId() {
		return super.getId() != null ? super.getId() : super.setId(UUID.randomUUID().toString()).getId();
	}
	
	public IQ setId(Long id) {
		super.setId(String.valueOf(id));
		return this;
	}

	@XmlElements({ @XmlElement(name = Ping.NAME, type = Ping.class), @XmlElement(name = Si.NAME, type = Si.class), @XmlElement(name = VCard.NAME, type = VCard.class), @XmlElement(name = Bind.NAME, type = Bind.class), @XmlElement(name = Session.NAME, type = Session.class), @XmlElement(name = Roster.NAME, type = Roster.class), @XmlElement(name = Register.NAME, type = Register.class), @XmlElement(name = DiscoInfo.NAME, type = DiscoInfo.class), @XmlElement(name = DiscoItems.NAME, type = DiscoItems.class), @XmlElement(name = Bytestreams.NAME, type = Bytestreams.class), @XmlElement(name = Blocked.NAME, type = Blocked.class), @XmlElement(name = UnBlock.NAME, type = UnBlock.class), @XmlElement(name = BlockList.NAME, type = BlockList.class) })
	public List<Protocol> getProtocols() {
		return this.protocols;
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

	@XmlElement
	public ServerError getError() {
		return super.getError();
	}

	public IQ clear() {
		this.protocols = null;
		return this;
	}

	@Override
	public void set(String localName, Object ob) {
		this.add(Protocol.class.cast(ob));
	}

	public Boolean isValid() {
		return ProtocolType.parse(this.getType()) != null;
	}
}
