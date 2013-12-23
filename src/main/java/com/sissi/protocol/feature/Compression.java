package com.sissi.protocol.feature;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Feature;

/**
 * @author kim 2013年12月17日
 */
@XmlRootElement
public class Compression implements Feature {

	public final static Compression FEATURE = new Compression("zlib");

	public final static String NAME = "compression";

	private final static String XMLNS = "http://jabber.org/features/compress";

	private Set<String> method;

	private Compression() {

	}

	private Compression(String... method) {
		super();
		this.method = new HashSet<String>();
		for (String each : method) {
			this.method.add(each);
		}
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@XmlElements({ @XmlElement(name = "method", type = String.class) })
	public Set<String> getMethod() {
		return this.method;
	}
}
