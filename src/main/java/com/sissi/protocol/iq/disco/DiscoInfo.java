package com.sissi.protocol.iq.disco;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = "http://jabber.org/protocol/disco#info", localName = "query")
@XmlRootElement(name = "query")
public class DiscoInfo extends Protocol {

	private final static String XMLNS = "http://jabber.org/protocol/disco#info";

	private List<Feature> feature;

	public DiscoInfo add(Feature feature) {
		if (this.feature == null) {
			this.feature = new ArrayList<Feature>();
		}
		this.feature.add(feature);
		return this;
	}

	@XmlElements({ @XmlElement(name = "feature", type = Blocking.class) })
	public List<Feature> getFeature() {
		return feature;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
