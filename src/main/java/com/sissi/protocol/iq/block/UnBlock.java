package com.sissi.protocol.iq.block;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Metadata;

/**
 * @author kim 2013年12月6日
 */
@Metadata(uri = Block.XMLNS, localName = UnBlock.NAME)
@XmlRootElement(name = UnBlock.NAME)
public class UnBlock extends Block {

	public final static String NAME = "unblock";

	public Boolean isUnBlockAll() {
		return super.getItem() == null;
	}
}