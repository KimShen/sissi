package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = Conflict.NAME)
public class Conflict extends ServerErrorDetail {

	public final static Conflict DETAIL_STREAM = new Conflict(XMLNS_STREAM);

	public final static Conflict DETAIL_ELEMENT = new Conflict(XMLNS_ELEMENT);

	public final static String NAME = "conflict";

	private Conflict() {

	}

	private Conflict(String xmlns) {
		super(xmlns);
	}
}
