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
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月5日
 */
@MappingMetadata(uri = Info.XMLNS, localName = Info.NAME)
@XmlRootElement(name = Info.NAME)
public class Info extends Protocol {

	public final static String XMLNS = "http://jabber.org/protocol/disco#info";

	public final static String NAME = "query";

	private List<Feature> feature;

	public Info add(Feature feature) {
		if (this.feature == null) {
			this.feature = new ArrayList<Feature>();
		}
		this.feature.add(feature);
		return this;
	}

	@XmlElements({ @XmlElement(name = "identity", type = Identity.class), @XmlElement(name = "feature", type = Blocking.class), @XmlElement(name = "feature", type = VCard.class), @XmlElement(name = "feature", type = Si.class), @XmlElement(name = "feature", type = SiFileTransfer.class), @XmlElement(name = "feature", type = Bytestreams.class) })
	public List<Feature> getFeature() {
		return feature;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
