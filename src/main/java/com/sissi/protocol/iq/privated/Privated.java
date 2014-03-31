package com.sissi.protocol.iq.privated;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月29日
 */
@Metadata(uri = Privated.XMLNS, localName = Privated.NAME)
@XmlRootElement(name = Privated.NAME)
public class Privated extends Protocol implements Collector {

	public final static String XMLNS = "jabber:iq:private";

	public final static String NAME = "query";

	private Storage storage;

	private Scratchpad scratchpad;

	@XmlElement
	public Storage getStorage() {
		return this.storage;
	}

	@XmlElement
	public Scratchpad getScratchpad() {
		return scratchpad;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case Storage.NAME:
			this.storage = Storage.class.cast(ob);
			return;
		case Scratchpad.NAME:
			this.scratchpad = Scratchpad.class.cast(ob);
			return;
		}
	}
}
