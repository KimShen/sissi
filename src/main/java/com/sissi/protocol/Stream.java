package com.sissi.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.feature.Bind;
import com.sissi.protocol.feature.Feature;
import com.sissi.protocol.feature.Mechanisms;
import com.sissi.protocol.feature.Register;
import com.sissi.protocol.feature.Session;
import com.sissi.read.Mapping.MappingMetadata;
import com.sissi.write.WriteWithOutClose;

/**
 * @author Kim.shen 2013-10-16
 */
@MappingMetadata(uri = "http://etherx.jabber.org/streams", localName = "stream")
@XmlRootElement(namespace = Stream.NAMESPACE)
public class Stream extends Protocol implements WriteWithOutClose {

	public final static String NAMESPACE = "http://etherx.jabber.org/streams";

	private final static String XMLNS = "jabber:client";

	private final static String VERSION = "1.0";

	private final AtomicBoolean isUsing = new AtomicBoolean();

	private List<Feature> features;

	private String version;

	public Stream() {
		super();
		this.isUsing.set(false);
	}

	public Stream(String id) {
		this();
		super.setId(id);
	}

	public Stream consume() {
		this.isUsing.set(true);
		return this;
	}

	public Boolean isUsing() {
		return this.isUsing.get();
	}

	@XmlAttribute
	public String getXmlns() {
		return XMLNS;
	}

	@XmlAttribute
	public String getVersion() {
		return this.version != null ? this.version : VERSION;
	}

	public Stream setVersion(String version) {
		this.version = version;
		return this;
	}

	public Stream addFeature(Feature feature) {
		if (this.features == null) {
			this.features = new ArrayList<Feature>();
		}
		this.features.add(feature);
		return this;
	}

	@XmlElementWrapper(namespace = Stream.NAMESPACE, name = "features")
	@XmlElements({ @XmlElement(name = "mechanisms", type = Mechanisms.class), @XmlElement(name = "bind", type = Bind.class), @XmlElement(name = "session", type = Session.class), @XmlElement(name = "register", type = Register.class) })
	public List<Feature> getFeatures() {
		return features;
	}
}
