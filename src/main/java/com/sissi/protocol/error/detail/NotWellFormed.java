package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = NotWellFormed.NAME)
public class NotWellFormed extends ServerErrorDetail {

	public final static NotWellFormed DETAIL = new NotWellFormed();

	public final static String NAME = "not-well-formed";

	private NotWellFormed() {
		super(XMLNS_STREAM);
	}
}
