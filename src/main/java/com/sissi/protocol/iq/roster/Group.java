package com.sissi.protocol.iq.roster;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Metadata;

/**
 * @author kim 2013-11-20
 */
@Metadata(uri = Roster.XMLNS, localName = Group.NAME)
@XmlRootElement
public class Group {

	public final static String NAME = "group";

	private String value;

	private GroupItem item;

	public Group() {
		super();
	}

	public Group(String text) {
		super();
		this.value = text;
	}

	Group item(GroupItem item) {
		this.item = item;
		return this;
	}

	@XmlValue
	public String getValue() {
		return this.value == null || this.value.isEmpty() ? null : this.value;
	}

	public Group setText(String text) {
		if (this.item != null && (text == null || text.isEmpty())) {
			this.item.getGroup().remove(this);
		} else {
			this.value = text;
		}
		this.item = null;
		return this;
	}

	public Group trimName(Integer length) {
		if (this.getValue() != null && this.getValue().length() > length) {
			this.value = this.value.substring(0, length);
		}
		return this;
	}
}
