package com.sissi.protocol.presence.group;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class X{

	public final static String XMLNS = "http://jabber.org/protocol/muc#user";

	private Item item;

	private List<Status> status;

	public X() {
		super();
	}

	public X(String affiliation, String role) {
		this.item = new Item(affiliation, role);
	}

	public X(String affiliation, String role, Integer... status) {
		this.item = new Item(affiliation, role);
		this.status = new ArrayList<Status>();
		for (Integer each : status) {
			this.status.add(new Status(each));
		}
	}

	@XmlElements({ @XmlElement(name = "status", type = Status.class) })
	public List<Status> getStatus() {
		return status;
	}

	@XmlElement(name = "item")
	public Item getItem() {
		return item;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
