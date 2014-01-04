package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月4日
 */
@XmlRootElement(name = UnSupportedVersion.NAME)
public class UnSupportedVersion extends StreamErrorDetail {

	public final static UnSupportedVersion DETAIL = new UnSupportedVersion();

	public final static String NAME = "unsupported-version";

	private UnSupportedVersion() {

	}
}
