package com.sissi.protocol.iq.block;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Item;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月16日
 */
@MappingMetadata(uri = Block.XMLNS, localName = Item.NAME)
@XmlRootElement
public class BlockListItem extends Item {

	public BlockListItem() {
		super();
	}

	public BlockListItem(String jid, String name, String subscription, String group) {
		super(jid, name, subscription);
	}
}