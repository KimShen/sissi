package com.sissi.protocol.iq.si;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2013年12月13日
 */
@Metadata(uri = File.XMLNS, localName = File.NAME)
@XmlRootElement
public class File implements Collector {

	public final static String XMLNS = "http://jabber.org/protocol/si/profile/file-transfer";

	public final static String NAME = "file";

	private String name;

	private String size;

	private Desc desc;

	@XmlElement
	public Desc getDesc() {
		return this.desc;
	}

	@XmlAttribute
	public String getName() {
		return this.name;
	}

	public File setName(String name) {
		this.name = name;
		return this;
	}

	@XmlAttribute
	public String getSize() {
		return this.size;
	}

	public File setSize(String size) {
		this.size = size;
		return this;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		this.desc = Desc.class.cast(ob);
	}
}
