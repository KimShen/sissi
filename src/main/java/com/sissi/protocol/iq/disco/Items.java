package com.sissi.protocol.iq.disco;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.feature.ItemClause;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013年12月18日
 */
@MappingMetadata(uri = Items.XMLNS, localName = Items.NAME)
@XmlRootElement(name = Items.NAME)
public class Items extends Protocol {

	public final static String XMLNS = "http://jabber.org/protocol/disco#items";

	public final static String NAME = "query";

	private List<Clause> clauses;

	public Items add(Clause clause) {
		if (this.clauses == null) {
			this.clauses = new ArrayList<Clause>();
		}
		this.clauses.add(clause);
		return this;
	}

	@XmlElements({ @XmlElement(name = ItemClause.NAME, type = ItemClause.class) })
	public List<Clause> getClauses() {
		return this.clauses;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
