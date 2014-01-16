package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月7日
 */
@XmlRootElement(name = NotAllowed.NAME)
public class NotAllowed extends ServerErrorDetail {

	public final static NotAllowed DETAIL = new NotAllowed();

	public final static String NAME = "not-allowed";

	private NotAllowed() {
		super(XMLNS_ELEMENT);
	}
}
