package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = JIDMalformed.NAME)
public class JIDMalformed extends ServerErrorDetail {

	public final static JIDMalformed DETAIL = new JIDMalformed();

	public final static String NAME = "jid-malformed";

	private JIDMalformed() {
		super(XMLNS_ELEMENT);
	}
}
