package com.sissi.protocol.iq.disco.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.disco.Feature;

/**
 * @author kim 2013年12月5日
 */
@XmlRootElement
public class VCard implements Feature {

	public final static VCard FEATURE = new VCard();

	private final static String VAR = "vcard-temp";

	private VCard() {

	}

	@Override
	@XmlAttribute
	public String getVar() {
		return VAR;
	}
}
