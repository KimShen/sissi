package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月4日
 */
@XmlRootElement(name = UnSupportedVersion.NAME)
public class UnSupportedVersion extends ServerErrorDetail {

	public final static UnSupportedVersion DETAIL = new UnSupportedVersion();

	public final static String NAME = "unsupported-version";

	private UnSupportedVersion() {
		super(XMLNS_STREAM);
	}
}
