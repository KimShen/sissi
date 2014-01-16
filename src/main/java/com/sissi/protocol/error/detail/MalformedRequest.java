package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = MalformedRequest.NAME)
public class MalformedRequest extends ServerErrorDetail {

	public final static MalformedRequest DETAIL = new MalformedRequest();

	public final static String NAME = "malformed-request";

	private MalformedRequest() {
		super(XMLNS_ELEMENT);
	}
}
