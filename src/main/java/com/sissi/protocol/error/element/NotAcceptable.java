package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2013年12月7日
 */
@XmlRootElement(name = NotAcceptable.NAME)
public class NotAcceptable extends ElementErrorDetail {

	public final static NotAcceptable DETAIL = new NotAcceptable();

	public final static String NAME = "not-acceptable";

	private NotAcceptable() {

	}
}
