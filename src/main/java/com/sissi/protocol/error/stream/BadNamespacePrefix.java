package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = BadNamespacePrefix.NAME)
public class BadNamespacePrefix extends StreamErrorDetail{

	public final static BadNamespacePrefix DETAIL = new BadNamespacePrefix();

	public final static String NAME = "bad-namespace-prefix";

	private BadNamespacePrefix() {

	}
}
