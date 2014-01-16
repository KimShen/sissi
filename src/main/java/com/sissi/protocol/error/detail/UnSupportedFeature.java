package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = UnSupportedFeature.NAME)
public class UnSupportedFeature extends ServerErrorDetail {

	public final static UnSupportedFeature DETAIL = new UnSupportedFeature();

	public final static String NAME = "unsupported-feature";

	private UnSupportedFeature() {
		super(XMLNS_STREAM);
	}
}
