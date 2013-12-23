package com.sissi.protocol.iq.disco;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.feature.Blocking;
import com.sissi.protocol.iq.disco.feature.Bytestreams;
import com.sissi.protocol.iq.disco.feature.Identity;
import com.sissi.protocol.iq.disco.feature.Si;
import com.sissi.protocol.iq.disco.feature.SiFileTransfer;
import com.sissi.protocol.iq.disco.feature.VCard;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = Info.XMLNS, localName = Info.NAME)
@XmlRootElement(name = Info.NAME)
public class Info extends Protocol {

	public final static String XMLNS = "http://jabber.org/protocol/disco#info";

	public final static String NAME = "query";

	private List<Clause> clauses;

	public Info add(Clause clause) {
		if (this.clauses == null) {
			this.clauses = new ArrayList<Clause>();
		}
		this.clauses.add(clause);
		return this;
	}

	@XmlElements({ @XmlElement(name = Identity.NAME, type = Identity.class), @XmlElement(name = Blocking.NAME, type = Blocking.class), @XmlElement(name = VCard.NAME, type = VCard.class), @XmlElement(name = Si.NAME, type = Si.class), @XmlElement(name = SiFileTransfer.NAME, type = SiFileTransfer.class), @XmlElement(name = Bytestreams.NAME, type = Bytestreams.class) })
	public List<Clause> getClause() {
		return this.clauses;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
