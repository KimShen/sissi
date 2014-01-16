package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = RemoteServerTimeout.NAME)
public class RemoteServerTimeout extends ServerErrorDetail {

	public final static RemoteServerTimeout DETAIL = new RemoteServerTimeout();

	public final static String NAME = "remote-server-timeout";

	private RemoteServerTimeout() {
		super(XMLNS_ELEMENT);
	}
}
