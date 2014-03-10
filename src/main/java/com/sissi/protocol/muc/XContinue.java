package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;

import com.sissi.read.Metadata;

/**
 * @author kim 2014年3月10日
 */
@Metadata(uri = XUser.XMLNS, localName = XContinue.NAME)
public class XContinue {

	public final static String NAME = "continue";

	private String thread;

	@XmlAttribute
	public String getThread() {
		return thread;
	}

	public XContinue setThread(String thread) {
		this.thread = thread;
		return this;
	}
}
