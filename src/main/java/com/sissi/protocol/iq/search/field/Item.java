package com.sissi.protocol.iq.search.field;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.config.Dictionary;
import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.protocol.iq.search.Search;

/**
 * @author kim 2014年6月8日
 */
@XmlType(namespace = Search.XMLNS)
@XmlRootElement(name = Item.NAME)
public class Item implements Field<String> {

	public final static String NAME = "item";

	private String jid;

	private String nick;

	private String last;

	private String first;

	private String email;

	public Item() {
		super();
	}

	public Item(String jid, String nick, String first, String last, String email) {
		super();
		this.jid = jid;
		this.nick = nick;
		this.last = last;
		this.first = first;
		this.email = email;

	}

	@Override
	@XmlElement(name = Dictionary.FIELD_NICK)
	public String getName() {
		return this.nick;
	}

	@Override
	@XmlAttribute(name = Dictionary.FIELD_JID)
	public String getValue() {
		return this.jid;
	}

	@XmlElement
	public String getFirst() {
		return this.first;
	}

	@XmlElement
	public String getLast() {
		return this.last;
	}

	@XmlElement
	public String getEmail() {
		return this.email;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public boolean hasChild() {
		return false;
	}
}
