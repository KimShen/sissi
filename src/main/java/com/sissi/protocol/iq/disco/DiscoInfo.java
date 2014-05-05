package com.sissi.protocol.iq.disco;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.io.read.Metadata;
import com.sissi.protocol.iq.data.XData;

/**
 * @author kim 2013年12月5日
 */
@Metadata(uri = DiscoInfo.XMLNS, localName = DiscoInfo.NAME)
@XmlRootElement(name = DiscoInfo.NAME)
public class DiscoInfo extends Disco {

	public final static String XMLNS = "http://jabber.org/protocol/disco#info";

	private String node;

	private XData data;

	public DiscoInfo() {
		super(XMLNS);
	}

	@XmlElements({ @XmlElement(name = DiscoFeature.NAME, type = DiscoFeature.class), @XmlElement(name = Identity.NAME, type = Identity.class) })
	public List<DiscoFeature> getDisco() {
		return super.getDisco();
	}

	public DiscoInfo data(XData data) {
		this.data = data;
		return this;
	}

	@XmlElement(name = XData.NAME)
	public XData getData() {
		return this.data;
	}

	public boolean node(String node) {
		return this.node != null && this.node.equals(node);
	}

	/**
	 * 用于Error.reply
	 * 
	 * @return
	 */
	@XmlAttribute
	public String getNode() {
		return this.node;
	}

	/**
	 * 客户端协议填充
	 * 
	 * @param node
	 * @return
	 */
	public DiscoInfo setNode(String node) {
		this.node = node;
		return this;
	}

}
