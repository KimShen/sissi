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
import com.sissi.read.Metadata;
import com.sissi.write.WriterFragement;
import com.sissi.write.WriterPart;

/**
 * @author Kim.shen 2013-10-16
 */
@Metadata(uri = Stream.XMLNS, localName = Stream.NAME)
@XmlRootElement(namespace = Stream.XMLNS)
@WriterFragement(part = WriterPart.WITHOUT_LAST)
public class Stream extends Protocol {

	public final static String XMLNS = "http://etherx.jabber.org/streams";

	public final static String NAME = "stream";

	private final String xmlns = "jabber:client";

	private final String version = "1.0";

	private final AtomicBoolean isUsing = new AtomicBoolean();

	private List<Feature> features;

	private String stream;

	public Stream consume() {
		this.isUsing.set(true);
		return this;
	}

	public Boolean isConsume() {
		return this.isUsing.get();
	}

	public String getId() {
		return super.getId() != null ? super.getId() : UUID.randomUUID().toString();
	}

	@XmlAttribute
	public String getXmlns() {
		return this.xmlns;
	}

	public boolean version(String version) {
		return version.compareTo(this.version) >= 0;
	}

	@XmlAttribute
	public String getVersion() {
		return this.version;
	}

	public Boolean validStream() {
		return this.stream != null && this.stream.equals(Stream.XMLNS);
	}
	
	public Stream setStream(String stream) {
		this.stream = stream;
		return this;
	}

	public Stream addFeature(Feature... features) {
		if (this.features == null) {
			this.features = new ArrayList<Feature>();
		}
		for (Feature feature : features) {
			this.features.add(feature);
		}
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

	public static Stream closeGraceFully() {
		return CloseGracefully.CLOSE;
	}

	public static Stream closeWhenOpening(Error error) {
		return new CloseWhenOpening(error);
	}

	public static Stream closeWhenRunning(Error error) {
		return new CloseWhenRunning(error);
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

	public static class CloseWhenOpening extends Stream {

		private CloseWhenOpening() {
		}

		private CloseWhenOpening(Error error) {
			super.setError(error);
		}
	}

	@WriterFragement(part = WriterPart.WITHOUT_FIRST)
	public static class CloseWhenRunning extends Stream {

		private CloseWhenRunning() {
		}

		private CloseWhenRunning(Error error) {
			super.setError(error);
		}
	}
}
