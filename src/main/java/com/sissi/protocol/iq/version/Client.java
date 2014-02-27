package com.sissi.protocol.iq.version;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年2月20日
 */
@Metadata(uri = Client.XMLNS, localName = Client.NAME)
@XmlType(namespace = Client.XMLNS)
@XmlRootElement(name = Client.NAME)
public class Client extends Protocol implements Collector {

	public final static String XMLNS = "jabber:iq:version";

	public final static String NAME = "query";

	private ClientOS os;

	private ClientName name;

	private ClientVersion version;

	@XmlElement(name = ClientOS.NAME)
	public ClientOS getOs() {
		return this.os;
	}

	@XmlElement(name = ClientName.NAME)
	public ClientName getName() {
		return this.name;
	}

	@XmlElement(name = ClientVersion.NAME)
	public ClientVersion getVersion() {
		return this.version;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case ClientOS.NAME:
			this.os = ClientOS.class.cast(ob);
			return;
		case ClientName.NAME:
			this.name = ClientName.class.cast(ob);
			return;
		case ClientVersion.NAME:
			this.version = ClientVersion.class.cast(ob);
			return;
		}
	}
}
