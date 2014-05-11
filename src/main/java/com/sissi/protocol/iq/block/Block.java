package com.sissi.protocol.iq.block;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.sissi.io.read.Collector;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月6日
 */
public class Block extends Protocol implements Collector {

	public final static String XMLNS = "urn:xmpp:blocking";

	private List<BlockListItem> item;

	public boolean item() {
		return this.getItem() != null && !this.getItem().isEmpty();
	}

	public Block add(BlockListItem item) {
		if (this.item == null) {
			this.item = new ArrayList<BlockListItem>();
		}
		this.item.add(item);
		return this;
	}

	@XmlElement
	public List<BlockListItem> getItem() {
		return this.item;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public Protocol parent() {
		return super.parent().setFrom((String) null);
	}

	@Override
	public void set(String localName, Object ob) {
		this.add(BlockListItem.class.cast(ob));
	}
}
