package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2013年12月7日
 */
@XmlRootElement(name = NotAcceptable.NAME)
public class NotAcceptable extends ServerErrorDetail {

	public final static NotAcceptable DETAIL = new NotAcceptable();

	public final static String NAME = "not-acceptable";

	private NotAcceptable() {
		super(XMLNS_ELEMENT);
	}
}
