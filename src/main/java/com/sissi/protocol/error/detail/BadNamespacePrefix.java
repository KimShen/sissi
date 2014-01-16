package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = BadNamespacePrefix.NAME)
public class BadNamespacePrefix extends ServerErrorDetail {

	public final static BadNamespacePrefix DETAIL = new BadNamespacePrefix();

	public final static String NAME = "bad-namespace-prefix";

	private BadNamespacePrefix() {
		super(XMLNS_STREAM);
	}
}
