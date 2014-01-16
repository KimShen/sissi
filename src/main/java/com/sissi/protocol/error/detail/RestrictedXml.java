package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = RestrictedXml.NAME)
public class RestrictedXml extends ServerErrorDetail {

	public final static RestrictedXml DETAIL = new RestrictedXml();

	public final static String NAME = "restricted-xml";

	private RestrictedXml() {
		super(XMLNS_STREAM);
	}
}
