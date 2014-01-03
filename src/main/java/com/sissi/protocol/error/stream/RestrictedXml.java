package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = RestrictedXml.NAME)
public class RestrictedXml extends StreamErrorDetail {

	public final static RestrictedXml DETAIL = new RestrictedXml();

	public final static String NAME = "restricted-xml";

	private RestrictedXml() {

	}
}
