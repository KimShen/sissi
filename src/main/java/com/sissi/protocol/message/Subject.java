package com.sissi.protocol.message;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.read.Metadata;

/**
 * @author kim 2014年1月29日
 */
@Metadata(uri = Message.XMLNS, localName = Subject.NAME)
@XmlRootElement
public class Subject {

	public final static String NAME = "subject";

	private final StringBuffer text = new StringBuffer();

	public Subject() {
		super();
	}

	public Subject(String text) {
		super();
		this.text.append(text);
	}

	@XmlValue
	public String getText() {
		return this.text.toString();
	}

	public Subject setText(String text) {
		this.text.append(text);
		return this;
	}

	public boolean hasContent() {
		return this.text.length() > 0;
	}
}
