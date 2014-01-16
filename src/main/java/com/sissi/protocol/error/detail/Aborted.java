package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = Aborted.NAME)
public class Aborted extends ServerErrorDetail {

	public final static Aborted DETAIL = new Aborted();

	public final static String NAME = "aborted";

	private Aborted() {
		super(XMLNS_ELEMENT);
	}
}
