package com.sissi.protocol.iq.disco;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.io.read.Metadata;

/**
 * @author kim 2013年12月18日
 */
@Metadata(uri = DiscoItems.XMLNS, localName = DiscoItems.NAME)
@XmlRootElement(name = DiscoItems.NAME)
public class DiscoItems extends Disco {

	public final static String XMLNS = "http://jabber.org/protocol/disco#items";

	private final static String rooms = "http://jabber.org/protocol/muc#rooms";

	private boolean node;

	public DiscoItems() {
		super(XMLNS);
	}

	@XmlElements({ @XmlElement(name = Item.NAME, type = Item.class) })
	public List<DiscoFeature> getDisco() {
		return super.getDisco();
	}

	public boolean node() {
		return this.node;
	}

	public DiscoItems setNode(String node) {
		this.node = rooms.equals(node);
		return this;
	}
}
