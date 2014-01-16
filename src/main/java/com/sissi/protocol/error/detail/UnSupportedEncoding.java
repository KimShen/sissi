package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = UnSupportedEncoding.NAME)
public class UnSupportedEncoding extends ServerErrorDetail {

	public final static UnSupportedEncoding DETAIL = new UnSupportedEncoding();

	public final static String NAME = "unsupported-encoding";

	private UnSupportedEncoding() {
		super(XMLNS_STREAM);
	}
}
