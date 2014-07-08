package com.sissi.protocol.muc;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.io.read.Metadata;
import com.sissi.protocol.Element;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2014年3月27日
 */
@Metadata(uri = Unique.XMLNS, localName = Unique.NAME)
public class Unique extends Protocol {

	public final static String NAME = "unique";

	public final static String XMLNS = "http://jabber.org/protocol/muc#unique";

	@XmlAttribute
	public String getXmlns() {
		return Unique.XMLNS;
	}

	public UniqueSequence unique() {
		return new UniqueSequence(this);
	}

	@XmlRootElement(name = Unique.NAME)
	public static class UniqueSequence implements Element {

		private Unique unique;

		public UniqueSequence() {
			super();
		}

		public UniqueSequence(Unique unique) {
			super();
			this.unique = unique;
		}

		@XmlValue
		public String getValue() {
			return UUID.randomUUID().toString();
		}

		@XmlAttribute
		public String getXmlns() {
			return Unique.XMLNS;
		}

		@Override
		@XmlTransient
		public String getId() {
			return null;
		}

		@Override
		public UniqueSequence setId(String id) {
			return this;
		}

		@Override
		@XmlTransient
		public String getFrom() {
			return null;
		}

		@Override
		public UniqueSequence setFrom(String from) {
			return this;
		}

		@Override
		@XmlTransient
		public String getTo() {
			return null;
		}

		@Override
		public UniqueSequence setTo(String to) {
			return this;
		}

		@Override
		@XmlTransient
		public String getType() {
			return null;
		}

		@Override
		public UniqueSequence setType(String type) {
			return this;
		}

		public Protocol parent() {
			return this.unique.parent().cast(IQ.class).clear().add(this);
		}
	}
}
