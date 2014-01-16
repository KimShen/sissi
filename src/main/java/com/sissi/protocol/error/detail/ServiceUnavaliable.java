package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = ServiceUnavaliable.NAME)
public class ServiceUnavaliable extends ServerErrorDetail {

	public final static ServiceUnavaliable DETAIL = new ServiceUnavaliable();

	public final static String NAME = "service-unavailable";

	private ServiceUnavaliable() {
		super(XMLNS_ELEMENT);
	}
}