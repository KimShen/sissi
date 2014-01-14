package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = FeatureNotImplemented.NAME)
public class FeatureNotImplemented extends ElementErrorDetail {

	public final static FeatureNotImplemented DETAIL = new FeatureNotImplemented();

	public final static String NAME = "feature-not-implemented";

	private FeatureNotImplemented() {

	}
}
