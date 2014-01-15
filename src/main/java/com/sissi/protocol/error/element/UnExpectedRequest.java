package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月15日
 */
@XmlRootElement(name = UnExpectedRequest.NAME)
public class UnExpectedRequest extends ElementErrorDetail {

	public final static UnExpectedRequest DETAIL = new UnExpectedRequest();

	public final static String NAME = "unexpected-request";

	private UnExpectedRequest() {

	}
}
