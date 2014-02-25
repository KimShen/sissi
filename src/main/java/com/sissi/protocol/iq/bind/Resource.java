package com.sissi.protocol.iq.bind;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;
import com.sissi.read.Metadata;

/**
 * @author kim 2013-10-30
 */
@Metadata(uri = Bind.XMLNS, localName = Resource.NAME)
@XmlRootElement
public class Resource extends Protocol {

	public final static String NAME = "resource";

	private String text;

	public Resource() {
		super();
	}

	public Resource(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public Resource setText(String text) {
		this.text = text;
		return this;
	}

	public boolean hasResource() {
		return this.text != null && !this.text.isEmpty();
	}
}
