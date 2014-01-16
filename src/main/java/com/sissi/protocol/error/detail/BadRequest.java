package com.sissi.protocol.error.detail;

import com.sissi.protocol.error.ServerErrorDetail;


/**
 * @author kim 2014年1月14日
 */
public class BadRequest extends ServerErrorDetail {

	public final static BadRequest DETAIL = new BadRequest();

	public final static String NAME = "bad-request";

	private BadRequest() {
		super(XMLNS_ELEMENT);
	}
}
