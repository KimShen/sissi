package com.sissi.protocol.iq.disco;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.disco.feature.Item;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013年12月18日
 */
@MappingMetadata(uri = DiscoItems.XMLNS, localName = DiscoItems.NAME)
@XmlRootElement(name = DiscoItems.NAME)
public class DiscoItems extends Disco {

	public final static String XMLNS = "http://jabber.org/protocol/disco#items";

	public DiscoItems() {
		super(XMLNS);
	}

	public DiscoItems add(DiscoFeature features) {
		super.add(features);
		return this;
	}

	@XmlElements({ @XmlElement(name = Item.NAME, type = Item.class) })
	public List<DiscoFeature> getDisco() {
		return super.getDisco();
	}
}
