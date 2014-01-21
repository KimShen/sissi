package com.sissi.protocol.iq.roster;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013-11-20
 */
@MappingMetadata(uri = Roster.XMLNS, localName = Group.NAME)
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

	Group setItem(GroupItem item) {
		this.item = item;
		return this;
	}

	@XmlValue
	public String getValue() {
		return this.value == null || this.value.isEmpty() ? null : this.value;
	}

	public Group setText(String text) {
		this.value = text;
		this.shouldIgnore();
		return this;
	}

	public Group trimName(Integer length) {
		if (this.getValue() != null && this.getValue().length() > length) {
			this.value = this.value.substring(0, length);
		}
		return this;
	}

	private void shouldIgnore() {
		if (this.item != null && this.getValue() == null) {
			this.item.getGroup().remove(this);
		}
		this.item = null;
	}
}
