package com.sissi.protocol.iq.disco.feature;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.iq.disco.Feature;

/**
 * @author kim 2013年12月13日
 */
@XmlRootElement
public class SiFileTransfer implements Feature {

	public final static SiFileTransfer FEATURE = new SiFileTransfer();

	public final static String NAME = "feature";

	private final static String VAR = "http://jabber.org/protocol/si/profile/file-transfer";

	private SiFileTransfer() {

	}

	@Override
	@XmlAttribute
	public String getVar() {
		return VAR;
	}
}
