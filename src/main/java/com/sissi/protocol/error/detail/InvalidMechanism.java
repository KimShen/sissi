package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = InvalidMechanism.NAME)
public class InvalidMechanism extends ServerErrorDetail {

	public final static InvalidMechanism DETAIL = new InvalidMechanism();

	public final static String NAME = "invalid-mechanism";

	private InvalidMechanism() {
		super(XMLNS_ELEMENT);
	}
}