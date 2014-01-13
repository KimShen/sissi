package com.sissi.protocol.error.element;

import com.sissi.protocol.error.ElementErrorDetail;


/**
 * @author kim 2014年1月14日
 */
public class BadRequest extends ElementErrorDetail {

	public final static BadRequest DETAIL = new BadRequest();

	public final static String NAME = "bad-request";

	private BadRequest() {

	}
}
