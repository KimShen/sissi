package com.sissi.protocol.iq.disco.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.disco.Feature;

/**
 * @author kim 2013年12月30日
 */
@XmlRootElement
public class Muc implements Feature {

	public final static Muc FEATURE = new Muc();

	public final static String NAME = "feature";

	private final static String VAR = "http://jabber.org/protocol/muc";

	private Muc() {

	}

	@Override
	@XmlAttribute
	public String getVar() {
		return VAR;
	}
}