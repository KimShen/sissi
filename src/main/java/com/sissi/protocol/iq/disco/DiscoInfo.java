package com.sissi.protocol.iq.disco;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.disco.feature.Blocking;
import com.sissi.protocol.iq.disco.feature.Bytestreams;
import com.sissi.protocol.iq.disco.feature.Identity;
import com.sissi.protocol.iq.disco.feature.Last;
import com.sissi.protocol.iq.disco.feature.Si;
import com.sissi.protocol.iq.disco.feature.SiFileTransfer;
import com.sissi.protocol.iq.disco.feature.VCard;
import com.sissi.protocol.iq.disco.feature.XData;
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

	@XmlElements({ @XmlElement(name = XData.NAME, type = XData.class), @XmlElement(name = Identity.NAME, type = Identity.class), @XmlElement(name = Blocking.NAME, type = Blocking.class), @XmlElement(name = VCard.NAME, type = VCard.class), @XmlElement(name = Si.NAME, type = Si.class), @XmlElement(name = SiFileTransfer.NAME, type = SiFileTransfer.class), @XmlElement(name = Bytestreams.NAME, type = Bytestreams.class), @XmlElement(name = Last.NAME, type = Last.class) })
	public List<DiscoFeature> getDisco() {
		return super.getDisco();
	}
}
