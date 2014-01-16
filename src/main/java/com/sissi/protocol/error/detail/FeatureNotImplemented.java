package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = FeatureNotImplemented.NAME)
public class FeatureNotImplemented extends ServerErrorDetail {

	public final static FeatureNotImplemented DETAIL = new FeatureNotImplemented();

	public final static String NAME = "feature-not-implemented";

	private FeatureNotImplemented() {
		super(XMLNS_ELEMENT);
	}
}
