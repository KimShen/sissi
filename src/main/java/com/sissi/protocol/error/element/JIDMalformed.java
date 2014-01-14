package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = JIDMalformed.NAME)
public class JIDMalformed extends ElementErrorDetail {

	public final static JIDMalformed DETAIL = new JIDMalformed();

	public final static String NAME = "jid-malformed";

	private JIDMalformed() {

	}
}
