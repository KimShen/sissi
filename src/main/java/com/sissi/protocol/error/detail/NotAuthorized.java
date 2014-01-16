package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月2日
 */
@XmlRootElement(name = NotAuthorized.NAME)
public class NotAuthorized extends ServerErrorDetail {

	public final static NotAuthorized DETAIL_STREAM = new NotAuthorized(XMLNS_STREAM);

	public final static NotAuthorized DETAIL_ELEMENT = new NotAuthorized(XMLNS_ELEMENT);

	public final static String NAME = "not-authorized";

	private NotAuthorized() {

	}

	private NotAuthorized(String xmlns) {
		super(XMLNS_STREAM);
	}
}
