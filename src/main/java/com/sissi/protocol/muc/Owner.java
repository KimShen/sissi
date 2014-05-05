package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.io.read.Collector;
import com.sissi.io.read.Metadata;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XData;

/**
 * @author kim 2014年3月24日
 */
@Metadata(uri = Owner.XMLNS, localName = Owner.NAME)
@XmlRootElement(name = Owner.NAME)
public class Owner extends Protocol implements Collector {

	public final static String NAME = "query";

	public final static String XMLNS = "http://jabber.org/protocol/muc#owner";

	private XData data;

	private Destory destory;

	public Owner() {
		super();
	}

	public Owner(XData data) {
		super();
		this.data = data;
	}

	@XmlElement
	public XData getX() {
		return this.data;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public boolean destory() {
		return this.getDestory() != null;
	}

	public Owner destory(Destory destory) {
		this.destory = destory;
		return this;
	}

	@XmlElement
	public Destory getDestory() {
		return this.destory;
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case XData.NAME:
			this.data = XData.class.cast(ob);
			return;
		case Destory.NAME:
			this.destory = Destory.class.cast(ob);
			return;
		}
	}
}
