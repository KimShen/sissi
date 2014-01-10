package com.sissi.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.feature.Bind;
import com.sissi.protocol.feature.Mechanisms;
import com.sissi.protocol.feature.Register;
import com.sissi.protocol.feature.Session;
import com.sissi.protocol.feature.Starttls;
import com.sissi.read.MappingMetadata;
import com.sissi.write.WriterFragement;
import com.sissi.write.WriterPart;

/**
 * @author Kim.shen 2013-10-16
 */
@MappingMetadata(uri = Stream.XMLNS, localName = Stream.NAME)
@XmlRootElement(namespace = Stream.XMLNS)
@WriterFragement(part = WriterPart.WITHOUT_LAST)
public class Stream extends Protocol {

	public final static String XMLNS = "http://etherx.jabber.org/streams";

	public final static String NAME = "stream";

	private final static String CONTENT_XMLNS = "jabber:client";

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

	public String getId() {
		return super.getId() != null ? super.getId() : UUID.randomUUID().toString();
	}

	@XmlAttribute
	public String getXmlns() {
		return CONTENT_XMLNS;
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

	public Boolean isValid() {
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
		return this.features;
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

	public static Stream closeSuddenly(Error error) {
		return new CloseSuddenly(error);
	}

	@WriterFragement(part = WriterPart.WITH_LAST)
	public static class CloseGracefully extends Stream {

		private final static CloseGracefully CLOSE = new CloseGracefully();

		private final List<Feature> EMPTY = new ArrayList<Feature>();

		private CloseGracefully() {

		}

		public List<Feature> getFeatures() {
			return EMPTY;
		}
	}

	public static class CloseForcible extends Stream {

		private CloseForcible() {
		}

		private CloseForcible(Error error) {
			super.setError(error);
		}
	}

	@WriterFragement(part = WriterPart.WITHOUT_FIRST)
	public static class CloseSuddenly extends Stream {

		private CloseSuddenly() {
		}

		private CloseSuddenly(Error error) {
			super.setError(error);
		}
	}
}
