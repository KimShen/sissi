package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = ServiceUnavaliable.NAME)
public class ServiceUnavaliable extends ElementErrorDetail {

	public final static ServiceUnavaliable DETAIL = new ServiceUnavaliable();

	public final static String NAME = "service-unavailable";

	private ServiceUnavaliable() {

	}
}