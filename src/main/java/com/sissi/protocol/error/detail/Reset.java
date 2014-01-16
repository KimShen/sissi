package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = Reset.NAME)
public class Reset extends ServerErrorDetail {

	public final static Reset DETAIL = new Reset();

	public final static String NAME = "reset";

	private Reset() {
		super(XMLNS_STREAM);
	}
}
