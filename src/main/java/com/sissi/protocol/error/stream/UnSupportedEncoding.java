package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = UnSupportedEncoding.NAME)
public class UnSupportedEncoding extends StreamErrorDetail {

	public final static UnSupportedEncoding DETAIL = new UnSupportedEncoding();

	public final static String NAME = "unsupported-encoding";

	private UnSupportedEncoding() {

	}
}
