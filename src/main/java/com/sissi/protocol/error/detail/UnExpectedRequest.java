package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月15日
 */
@XmlRootElement(name = UnExpectedRequest.NAME)
public class UnExpectedRequest extends ServerErrorDetail {

	public final static UnExpectedRequest DETAIL = new UnExpectedRequest();

	public final static String NAME = "unexpected-request";

	private UnExpectedRequest() {
		super(XMLNS_ELEMENT);
	}
}
