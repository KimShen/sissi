package com.sissi.protocol.iq.bytestreams;

import com.sissi.protocol.Protocol;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013年12月24日
 */
@MappingMetadata(uri = Bytestreams.XMLNS, localName = StreamhostActivate.NAME)
public class StreamhostActivate extends Protocol {
	
	public final static String NAME = "activate";

	private String text;

	public String getText() {
		return text;
	}

	public StreamhostActivate setText(String text) {
		this.text = text;
		return this;
	}
}
