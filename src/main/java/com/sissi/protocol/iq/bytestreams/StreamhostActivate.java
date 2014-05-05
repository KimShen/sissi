package com.sissi.protocol.iq.bytestreams;

import com.sissi.io.read.Metadata;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月24日
 */
@Metadata(uri = Bytestreams.XMLNS, localName = StreamhostActivate.NAME)
public class StreamhostActivate extends Protocol {
	
	public final static String NAME = "activate";

	private String text;

	public String getText() {
		return this.text;
	}

	public StreamhostActivate setText(String text) {
		this.text = text;
		return this;
	}
}
