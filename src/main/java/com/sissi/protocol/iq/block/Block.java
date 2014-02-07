package com.sissi.protocol.iq.block;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Collector;

/**
 * @author kim 2013年12月6日
 */
public class Block extends Protocol implements Collector {

	public final static String XMLNS = "urn:xmpp:blocking";

	private BlockListItem item;

	@XmlElement
	public BlockListItem getItem() {
		return this.item;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		this.item = BlockListItem.class.cast(ob);
	}
}
