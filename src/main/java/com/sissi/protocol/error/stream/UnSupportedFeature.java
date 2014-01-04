package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = UnSupportedFeature.NAME)
public class UnSupportedFeature extends StreamErrorDetail {

	public final static UnSupportedFeature DETAIL = new UnSupportedFeature();

	public final static String NAME = "unsupported-feature";

	private UnSupportedFeature() {

	}
}
