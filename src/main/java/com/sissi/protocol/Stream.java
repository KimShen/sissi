package com.sissi.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.feature.Bind;
import com.sissi.protocol.feature.Mechanisms;
import com.sissi.protocol.feature.Register;
import com.sissi.protocol.feature.Session;
import com.sissi.protocol.feature.Starttls;
import com.sissi.read.MappingMetadata;
import com.sissi.write.WithFull;
import com.sissi.write.WithJustClose;
import com.sissi.write.WithOutClose;

/**
 * @author Kim.shen 2013-10-16
 */
@MappingMetadata(uri = Stream.XMLNS, localName = Stream.NAME)
@XmlRootElement(namespace = Stream.XMLNS)
public class Stream extends Protocol implements WithOutClose {

	public final static String XMLNS = "http://etherx.jabber.org/streams";

	public final static String NAME = "stream";

	private final static String SUB_XMLNS = "jabber:client";

	private final static String VERSION = "1.0";

	private final AtomicBoolean isUsing = new AtomicBoolean();

	private List<Feature> features;

	private String lang;

	private String stream;

	public Stream() {
		super();
		this.isUsing.set(false);
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
		return SUB_XMLNS;
	}

	@XmlAttribute
	public String getVersion() {
		return VERSION;
	}

	@XmlAttribute(name = "xml:lang")
	public String getLang() {
		return this.lang;
	}

	public Stream setLang(String lang) {
		this.lang = lang;
		return this;
	}

	public Stream setStream(String stream) {
		this.stream = stream;
		return this;
	}

	public Boolean isValidXmlns() {
		return this.stream != null && this.stream.equals(Stream.XMLNS);
	}

	public Stream addFeature(Feature feature) {
		if (this.features == null) {
			this.features = new ArrayList<Feature>();
		}
		this.features.add(feature);
		return this;
	}

	@XmlElementWrapper(namespace = Stream.XMLNS, name = "features")
	@XmlElements({ @XmlElement(name = Starttls.NAME, type = Starttls.class), @XmlElement(name = Mechanisms.NAME, type = Mechanisms.class), @XmlElement(name = Bind.NAME, type = Bind.class), @XmlElement(name = Session.NAME, type = Session.class), @XmlElement(name = Register.NAME, type = Register.class) })
	public List<Feature> getFeatures() {
		return features;
	}

	@XmlElement(namespace = Stream.XMLNS, name = ServerError.NAME)
	public ServerError getError() {
		return super.getError();
	}

	public static Stream closeGracefully() {
		return CloseGracefully.CLOSE;
	}

	public static Stream closeForcible(Error error) {
		return new CloseForcible(error);
	}

	@XmlType(namespace = CloseGracefully.XMLNS)
	@XmlRootElement(name = Stream.NAME)
	private static class CloseGracefully extends Stream implements WithJustClose {

		private final static CloseGracefully CLOSE = new CloseGracefully();

		public final static String XMLNS = "http://etherx.jabber.org/streams/closeGracefully";

		private CloseGracefully() {

		}

		@XmlElement
		public String getPlaceholder() {
			return "";
		}
	}

	@XmlType(namespace = CloseForcible.XMLNS)
	@XmlRootElement(name = Stream.NAME)
	private static class CloseForcible extends Stream implements WithFull {

		public final static String XMLNS = "http://etherx.jabber.org/streams/closeForcible";

		private CloseForcible() {
		}

		private CloseForcible(Error error) {
			super.setError(error);
		}
	}
}
