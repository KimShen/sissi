package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月2日
 */
@XmlRootElement(name = NotAuthorized.NAME)
public class NotAuthorized extends StreamErrorDetail {

	public final static NotAuthorized DETAIL = new NotAuthorized();

	public final static String NAME = "not-authorized";

	private NotAuthorized() {

	}
}
