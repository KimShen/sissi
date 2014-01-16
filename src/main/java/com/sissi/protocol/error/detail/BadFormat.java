package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = BadFormat.NAME)
public class BadFormat extends ServerErrorDetail {

	public final static BadFormat DETAIL = new BadFormat();

	public final static String NAME = "bad-format";

	private BadFormat() {
		super(XMLNS_STREAM);
	}
}
