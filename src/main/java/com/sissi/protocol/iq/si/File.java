package com.sissi.protocol.iq.si;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013年12月13日
 */
@MappingMetadata(uri = File.XMLNS, localName = File.NAME)
@XmlRootElement
public class File {

	public final static String XMLNS = "http://jabber.org/protocol/si/profile/file-transfer";

	public final static String NAME = "file";
	
	private String name;

	private String size;

	@XmlAttribute
	public String getName() {
		return name;
	}

	public File setName(String name) {
		this.name = name;
		return this;
	}

	@XmlAttribute
	public String getSize() {
		return size;
	}

	public File setSize(String size) {
		this.size = size;
		return this;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}
}
