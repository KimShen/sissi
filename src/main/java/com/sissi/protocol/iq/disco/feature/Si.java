package com.sissi.protocol.iq.disco.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.disco.Feature;

/**
 * @author kim 2013年12月13日
 */
@XmlRootElement
public class Si implements Feature {
	
	public final static Si FEATURE = new Si();

	private final static String VAR = "http://jabber.org/protocol/si";

	private Si() {

	}

	@Override
	@XmlAttribute
	public String getVar() {
		return VAR;
	}
}
