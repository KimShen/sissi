package com.sissi.protocol.iq.privated;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.io.read.Metadata;

/**
 * @author kim 2014年3月31日
 */
@Metadata(uri = Scratchpad.XMLNS, localName = Scratchpad.NAME)
@XmlRootElement
public class Scratchpad {

	public final static String XMLNS = "scratchpad:tasks";

	public final static String NAME = "scratchpad";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
