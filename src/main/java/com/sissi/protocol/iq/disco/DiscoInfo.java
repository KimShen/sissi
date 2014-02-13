package com.sissi.protocol.iq.disco;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Metadata;

/**
 * @author kim 2013年12月5日
 */
@Metadata(uri = DiscoInfo.XMLNS, localName = DiscoInfo.NAME)
@XmlRootElement(name = DiscoInfo.NAME)
public class DiscoInfo extends Disco {

	public final static String XMLNS = "http://jabber.org/protocol/disco#info";

	public DiscoInfo() {
		super(XMLNS);
	}

	@XmlElements({ @XmlElement(name = DiscoFeature.NAME, type = DiscoFeature.class), @XmlElement(name = Identity.NAME, type = Identity.class) })
	public List<DiscoFeature> getDisco() {
		return super.getDisco();
	}
}
