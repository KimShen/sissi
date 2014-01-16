package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = InvalidXml.NAME)
public class InvalidXml extends ServerErrorDetail {

	public final static InvalidXml DETAIL = new InvalidXml();

	public final static String NAME = "invalid-xml";

	private InvalidXml() {
		super(XMLNS_STREAM);
	}
}
