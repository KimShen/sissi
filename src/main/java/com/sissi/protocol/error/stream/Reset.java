package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = Reset.NAME)
public class Reset extends StreamErrorDetail {

	public final static Reset DETAIL = new Reset();

	public final static String NAME = "reset";

	private Reset() {

	}
}
