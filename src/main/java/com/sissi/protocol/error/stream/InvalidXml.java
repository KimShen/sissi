package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = InvalidXml.NAME)
public class InvalidXml extends StreamErrorDetail {

	public final static InvalidXml DETAIL = new InvalidXml();

	public final static String NAME = "invalid-xml";

	private InvalidXml() {

	}
}
