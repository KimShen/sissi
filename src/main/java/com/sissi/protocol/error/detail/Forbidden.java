package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = Forbidden.NAME)
public class Forbidden extends ServerErrorDetail {

	public final static Forbidden DETAIL = new Forbidden();

	public final static String NAME = "forbidden";

	private Forbidden() {
		super(XMLNS_ELEMENT);
	}
}
