package com.sissi.protocol.iq.si;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.codec.digest.DigestUtils;

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

	private String source;

	private Feature feature;

	private File file;

	@XmlAttribute
	public String getSource() {
		return source;
	}

	public Si setSource(String source) {
		this.source = source;
		return this;
	}

	public Si setId(String id) {
		super.setId(id);
		return this;
	}

	@XmlAttribute
	public String getProfile() {
		return this.profile;
	}

	public Si setProfile(String profile) {
		this.profile = profile;
		return this;
	}

	public Si setFile(File file) {
		this.file = file;
		return this;
	}

	@XmlElement
	public File getFile() {
		return this.file;
	}

	public Si setFeature(Feature feature) {
		this.feature = feature;
		return this;
	}

	@XmlElement
	public Feature getFeature() {
		return this.feature;
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	public String host(String from, String to) {
		return DigestUtils.sha1Hex(this.getId() + from + to);
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
