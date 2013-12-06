package com.sissi.protocol.iq.block;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.roster.Item;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月6日
 */
@MappingMetadata(uri = "urn:xmpp:blocking", localName = "blocklist")
@XmlRootElement(name = "blocklist")
public class BlockList extends Protocol {

	private final static String XMLNS = "urn:xmpp:blocking";

	private List<Item> item;

	public BlockList add(Item item) {
		if (this.item == null) {
			this.item = new ArrayList<Item>();
		}
		this.item.add(item);
		return this;
	}

	@XmlElements({ @XmlElement(name = "item", type = Item.class) })
	public List<Item> getItem() {
		return item;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
