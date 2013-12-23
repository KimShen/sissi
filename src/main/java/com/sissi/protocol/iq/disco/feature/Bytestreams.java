package com.sissi.protocol.iq.disco.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.disco.Feature;

/**
 * @author kim 2013年12月13日
 */
@XmlRootElement
public class Bytestreams implements Feature {

	public final static Bytestreams FEATURE = new Bytestreams();

	public final static String NAME = "feature";

	private final static String VAR = "http://jabber.org/protocol/bytestreams";

	private Bytestreams() {

	}

	@Override
	@XmlAttribute
	public String getVar() {
		return VAR;
	}
}
