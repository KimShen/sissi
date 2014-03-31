package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;

import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月10日
 */
@Metadata(uri = XUser.XMLNS, localName = Continue.NAME)
public class Continue {

	public final static String NAME = "continue";

	private String thread;

	public Continue() {
		super();
	}

	public Continue(String thread) {
		super();
		this.thread = thread;
	}

	@XmlAttribute
	public String getThread() {
		return thread;
	}

	public Continue setThread(String thread) {
		this.thread = thread;
		return this;
	}
}
