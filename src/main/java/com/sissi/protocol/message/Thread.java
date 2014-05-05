package com.sissi.protocol.message;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.sissi.io.read.Metadata;

/**
 * @author kim 2014年1月28日
 */
@Metadata(uri = Message.XMLNS, localName = Thread.NAME)
@XmlRootElement
public class Thread {

	public final static String NAME = "thread";

	private String text;

	private String parent;

	public Thread() {
		super();
	}

	public Thread(String text) {
		super();
		this.text = text;
	}

	public Thread(String text, String parent) {
		super();
		this.text = text;
		this.parent = parent;
	}

	@XmlValue
	public String getText() {
		return this.text;
	}

	public Thread setText(String text) {
		this.text = text;
		return this;
	}

	@XmlAttribute
	public String getParent() {
		return this.parent;
	}

	public Thread setParent(String parent) {
		this.parent = parent;
		return this;
	}

	public boolean content() {
		return this.text != null && this.text.length() > 0;
	}
}
