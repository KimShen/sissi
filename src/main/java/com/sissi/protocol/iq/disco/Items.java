package com.sissi.protocol.iq.disco;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.disco.feature.ItemClause;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月18日
 */
@MappingMetadata(uri = Items.XMLNS, localName = Items.NAME)
@XmlRootElement(name = Items.NAME)
public class Items extends Protocol {

	public final static String XMLNS = "http://jabber.org/protocol/disco#items";

	public final static String NAME = "query";

	private List<Feature> feature;

	public Items add(Feature feature) {
		if (this.feature == null) {
			this.feature = new ArrayList<Feature>();
		}
		this.feature.add(feature);
		return this;
	}

	@XmlElements({ @XmlElement(name = "item", type = ItemClause.class) })
	public List<Feature> getFeature() {
		return feature;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
