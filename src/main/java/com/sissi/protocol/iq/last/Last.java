package com.sissi.protocol.iq.last;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.protocol.Element;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.IQ;
import com.sissi.read.Metadata;

/**
 * @author kim 2014年2月9日
 */
@Metadata(uri = Last.XMLNS, localName = Last.NAME)
public class Last extends Protocol {

	public final static String XMLNS = "jabber:iq:last";

	public final static String NAME = "query";

	public LastSeconds seconds() {
		return new LastSeconds(this);
	}

	@XmlRootElement(name = Last.NAME)
	public static class LastSeconds implements Element {

		private Last last;

		private String seconds;

		private String text;

		public LastSeconds() {
			super();
		}

		public LastSeconds(Last last) {
			super();
			this.last = last;
		}

		private String computeSeconds(long seconds) {
			return String.valueOf((System.currentTimeMillis() - seconds) / 1000);
		}

		public LastSeconds seconds(long seconds) {
			this.seconds = this.computeSeconds(seconds);
			return this;
		}

		public LastSeconds seconds(String seconds) {
			this.seconds = this.computeSeconds(Long.valueOf(seconds));
			return this;
		}

		public LastSeconds setSeconds(String seconds) {
			this.seconds = seconds;
			return this;
		}

		public LastSeconds setText(String text) {
			this.text = text;
			return this;
		}

		@XmlAttribute
		public String getSeconds() {
			return this.seconds;
		}

		@XmlValue
		public String getText() {
			return this.text;
		}

		@XmlAttribute
		public String getXmlns() {
			return Last.XMLNS;
		}

		@Override
		@XmlTransient
		public String getId() {
			return this.last.getId();
		}

		@Override
		public LastSeconds setId(String id) {
			this.last.setId(id);
			return this;
		}

		@Override
		@XmlTransient
		public String getFrom() {
			return this.last.getFrom();
		}

		@Override
		public LastSeconds setFrom(String from) {
			this.last.setFrom(from);
			return this;
		}

		@Override
		@XmlTransient
		public String getTo() {
			return this.last.getTo();
		}

		@Override
		public LastSeconds setTo(String to) {
			this.last.setTo(to);
			return this;
		}

		@Override
		@XmlTransient
		public String getType() {
			return this.last.getType();
		}

		@Override
		public LastSeconds setType(String type) {
			this.last.setType(type);
			return this;
		}
		
		@XmlTransient
		public Protocol getParent(){
			return IQ.class.cast(this.last.getParent()).clear().add(this);
		}
	}
}
