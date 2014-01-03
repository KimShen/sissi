package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = NotWellFormed.NAME)
public class NotWellFormed extends StreamErrorDetail {

	public final static NotWellFormed DETAIL = new NotWellFormed();

	public final static String NAME = "not-well-formed";

	private NotWellFormed() {

	}
}
