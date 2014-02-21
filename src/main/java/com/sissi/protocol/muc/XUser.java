package com.sissi.protocol.muc;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.context.JID;
import com.sissi.protocol.presence.X;
import com.sissi.read.Metadata;
import com.sissi.ucenter.MucStatus;
import com.sissi.ucenter.MucStatusCollector;
import com.sissi.ucenter.MucStatusJudge;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;

/**
 * @author kim 2014年2月11日
 */
@Metadata(uri = XUser.XMLNS, localName = X.NAME)
@XmlType(namespace = XUser.XMLNS)
@XmlRootElement
public class XUser extends X implements Field<String>, MucStatus {

	public final static String XMLNS = "http://jabber.org/protocol/muc#user";

	private Item item;

	private String current;

	private Set<ItemStatus> statuses;

	public XUser() {
		super();
	}

	public XUser(JID current) {
		this(current.asStringWithBare());
	}

	public XUser(String current) {
		this.current = current;
	}

	public XUser setItem(Item item, MucStatusCollector collector) {
		this.item = item;
		collector.collect(this, this.item);
		return this;
	}

	public XUser add(String code) {
		if (this.statuses == null) {
			this.statuses = new HashSet<ItemStatus>();
		}
		this.statuses.add(ItemStatus.parse(code));
		return this;
	}

	@XmlElement
	public Item getItem() {
		return this.item;
	}

	@XmlElements({ @XmlElement(name = ItemStatus.NAME, type = ItemStatus.class) })
	public Set<ItemStatus> getStatuses() {
		return this.statuses;
	}

	@Override
	public String getXmlns() {
		return XMLNS;
	}

	public Object supply(String key) {
		return null;
	}

	public boolean judge(String key, Object value) {
		return MucStatusJudge.JUDEGE_JID.equals(key) ? (this.current != null ? this.current.equals(value.toString()) : false) : false;
	}

	@Override
	public String getValue() {
		return null;
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
