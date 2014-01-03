package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = InvalidFrom.NAME)
public class InvalidFrom extends StreamErrorDetail {

	public final static InvalidFrom DETAIL = new InvalidFrom();

	public final static String NAME = "invalid-from";

	private InvalidFrom() {

	}
}
