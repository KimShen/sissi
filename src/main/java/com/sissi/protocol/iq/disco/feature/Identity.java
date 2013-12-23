package com.sissi.protocol.iq.disco.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.disco.Clause;

/**
 * @author kim 2013年12月20日
 */
@XmlRootElement
public class Identity implements Clause {

	public final static Identity FEATURE = new Identity();

	public final static String NAME = "identity";

	private final String category = "proxy";

	private final String type = "bytestreams";

	private final String name = "SOCKS5 Bytestreams Service";

	@XmlAttribute
	public String getCategory() {
		return this.category;
	}

	@XmlAttribute
	public String getType() {
		return this.type;
	}

	@XmlAttribute
	public String getName() {
		return this.name;
	}
}
