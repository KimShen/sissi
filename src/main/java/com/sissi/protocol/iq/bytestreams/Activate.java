package com.sissi.protocol.iq.bytestreams;

import com.sissi.protocol.Protocol;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2013年12月24日
 */
@MappingMetadata(uri = Bytestreams.XMLNS, localName = Activate.NAME)
public class Activate extends Protocol {
	
	public final static String NAME = "activate";

	private String text;

	public String getText() {
		return text;
	}

	public Activate setText(String text) {
		this.text = text;
		return this;
	}
}
