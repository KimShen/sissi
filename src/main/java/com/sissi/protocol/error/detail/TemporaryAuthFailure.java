package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = TemporaryAuthFailure.NAME)
public class TemporaryAuthFailure extends ServerErrorDetail {

	public final static TemporaryAuthFailure DETAIL = new TemporaryAuthFailure();

	public final static String NAME = "temporary-auth-failure";

	public TemporaryAuthFailure() {
		super(XMLNS_ELEMENT);
	}
}
