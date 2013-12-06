package com.sissi.protocol.iq.block;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.read.Collector;

/**
 * @author kim 2013年12月6日
 */
public class Block extends Protocol implements Collector {

	private final static String XMLNS = "urn:xmpp:blocking";

	private Item item;

	@XmlElement
	public Item getItem() {
		return item;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		this.item = Item.class.cast(ob);
	}
}
