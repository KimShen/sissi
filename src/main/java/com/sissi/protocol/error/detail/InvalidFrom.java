package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = InvalidFrom.NAME)
public class InvalidFrom extends ServerErrorDetail {

	public final static InvalidFrom DETAIL = new InvalidFrom();

	public final static String NAME = "invalid-from";

	private InvalidFrom() {
		super(XMLNS_STREAM);
	}
}
