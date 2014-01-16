package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = RemoteServerNotFound.NAME)
public class RemoteServerNotFound extends ServerErrorDetail {

	public final static RemoteServerNotFound DETAIL = new RemoteServerNotFound();

	public final static String NAME = "remote-server-not-found";

	private RemoteServerNotFound() {
		super(XMLNS_ELEMENT);
	}
}
