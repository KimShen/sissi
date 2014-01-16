package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = HostUnknown.NAME)
public class HostUnknown extends ServerErrorDetail {

	public final static HostUnknown DETAIL = new HostUnknown();

	public final static String NAME = "host-unknown";

	private HostUnknown() {
		super(XMLNS_STREAM);
	}
}
