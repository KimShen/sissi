package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = ServiceUnavailable.NAME)
public class ServiceUnavailable extends ServerErrorDetail {

	public final static ServiceUnavailable DETAIL = new ServiceUnavailable();

	public final static String NAME = "service-unavailable";

	private ServiceUnavailable() {
		super(XMLNS_ELEMENT);
	}
}