package com.sissi.protocol.iq.disco.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.disco.Feature;

/**
 * @author kim 2013年12月20日
 */
@XmlRootElement
public class Identity implements Feature {

	@XmlAttribute
	public String getCategory() {
		return "proxy";
	}

	@XmlAttribute
	public String getType() {
		return "bytestreams";
	}

	@XmlAttribute
	public String getName() {
		return "SOCKS5 Bytestreams Service";
	}

	@Override
	@XmlAttribute
	public String getVar() {
		return null;
	}

}
