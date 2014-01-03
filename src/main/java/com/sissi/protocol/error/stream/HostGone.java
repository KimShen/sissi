package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = HostGone.NAME)
public class HostGone extends StreamErrorDetail {

	public final static HostGone DETAIL = new HostGone();

	public final static String NAME = "host-gone";

	private HostGone() {

	}
}
