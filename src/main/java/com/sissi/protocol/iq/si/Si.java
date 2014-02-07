package com.sissi.protocol.iq.si;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.read.Collector;
import com.sissi.read.Metadata;

/**
 * @author kim 2013年12月13日
 */
@Metadata(uri = Si.XMLNS, localName = Si.NAME)
@XmlType(namespace = Stream.XMLNS)
@XmlRootElement
public class Si extends Protocol implements Collector {

	public final static String XMLNS = "http://jabber.org/protocol/si";

	public final static String NAME = "si";

	private String profile;

	private Feature feature;

	private File file;

	@XmlAttribute
	public String getProfile() {
		return this.profile;
	}

	public Si setProfile(String profile) {
		this.profile = profile;
		return this;
	}

	@XmlElement
	public File getFile() {
		return this.file;
	}

	@XmlElement
	public Feature getFeature() {
		return this.feature;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@Override
	public void set(String localName, Object ob) {
		switch (localName) {
		case File.NAME:
			this.file = File.class.cast(ob);
			break;
		case Feature.NAME:
			this.feature = Feature.class.cast(ob);
			break;
		}
	}
}
