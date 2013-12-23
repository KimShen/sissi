package com.sissi.protocol.iq.block;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Item;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013年12月16日
 */
@MappingMetadata(uri = Block.XMLNS, localName = Item.NAME)
@XmlType(namespace = Block.XMLNS)
@XmlRootElement(name = Item.NAME)
public class BlockListItem extends Item {

	private String subscription;

	public BlockListItem() {
		super();
	}

	public BlockListItem(String jid, String name, String subscription, String group) {
		super(jid, name);
		this.subscription = subscription;
	}

	public BlockListItem setJid(String jid) {
		super.setJid(jid);
		return this;
	}

	@XmlAttribute
	public String getSubscription() {
		return this.subscription;
	}

	public BlockListItem setSubscription(String subscription) {
		this.subscription = subscription;
		return this;
	}
}