package com.sissi.protocol.iq.disco.feature;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Item;
import com.sissi.protocol.iq.disco.Feature;
import com.sissi.protocol.iq.disco.Items;

/**
 * @author kim 2013年12月18日
 */
@XmlType(namespace = Items.XMLNS)
@XmlRootElement(name = Item.NAME)
public class ItemClause extends Item implements Feature {

	public ItemClause() {
		super();
	}

	public ItemClause(String jid, String name) {
		super(jid, name);
	}

	@Override
	public String getVar() {
		return Items.XMLNS;
	}
}
