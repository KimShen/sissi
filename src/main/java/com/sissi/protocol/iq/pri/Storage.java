package com.sissi.protocol.iq.pri;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月31日
 */
@Metadata(uri = Storage.XMLNS, localName = Storage.NAME)
@XmlRootElement
public class Storage {

	public final static String XMLNS = "storage:bookmarks";

	public final static String NAME = "storage";

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}