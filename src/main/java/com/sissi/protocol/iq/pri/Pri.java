package com.sissi.protocol.iq.pri;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月29日
 */
@Metadata(uri = Pri.XMLNS, localName = Pri.NAME)
@XmlRootElement(name = Pri.NAME)
public class Pri extends Protocol implements Collector {

	public final static String XMLNS = "jabber:iq:private";

	public final static String NAME = "query";

	private Storage storage;

	@XmlElement
	public Storage getStorage() {
		return this.storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
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
		}
	}
}
