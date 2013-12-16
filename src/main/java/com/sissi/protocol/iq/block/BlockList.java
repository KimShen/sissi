package com.sissi.protocol.iq.block;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Mapping.MappingMetadata;

/**
 * @author kim 2013年12月6日
 */
@MappingMetadata(uri = Block.XMLNS, localName = BlockList.NAME)
@XmlRootElement(name = BlockList.NAME)
public class BlockList extends Protocol {

	public final static String NAME = "blocklist";

	private List<BlockListItem> item;

	public BlockList add(BlockListItem item) {
		if (this.item == null) {
			this.item = new ArrayList<BlockListItem>();
		}
		this.item.add(item);
		return this;
	}

	@XmlElements({ @XmlElement(name = "item", type = BlockListItem.class) })
	public List<BlockListItem> getItem() {
		return item;
	}

	@XmlAttribute
	public String getXmlns() {
		return Block.XMLNS;
	}
}
