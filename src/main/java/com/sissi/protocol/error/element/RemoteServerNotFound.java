package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = RemoteServerNotFound.NAME)
public class RemoteServerNotFound extends ElementErrorDetail {

	public final static RemoteServerNotFound DETAIL = new RemoteServerNotFound();

	public final static String NAME = "remote-server-not-found";

	private RemoteServerNotFound() {

	}
}
