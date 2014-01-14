package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = SubscriptionRequired.NAME)
public class SubscriptionRequired extends ElementErrorDetail {

	public final static SubscriptionRequired DETAIL = new SubscriptionRequired();

	public final static String NAME = "subscription-required";

	private SubscriptionRequired() {

	}
}
