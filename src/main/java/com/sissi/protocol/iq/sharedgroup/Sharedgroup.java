package com.sissi.protocol.iq.sharedgroup;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Metadata;

/**
 * @author Kim.shen 2013-10-20
 */
@Metadata(uri = Sharedgroup.XMLNS, localName = Sharedgroup.NAME)
@XmlRootElement
public class Sharedgroup extends Protocol {

	public final static String XMLNS = "http://www.jivesoftware.org/protocol/sharedgroup";

	public final static String NAME = "sharedgroup";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
