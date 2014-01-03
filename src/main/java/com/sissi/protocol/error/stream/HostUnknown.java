package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = HostUnknown.NAME)
public class HostUnknown extends StreamErrorDetail {

	public final static HostUnknown DETAIL = new HostUnknown();

	public final static String NAME = "host-unknown";

	private HostUnknown() {

	}
}
