package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = SubscriptionRequired.NAME)
public class SubscriptionRequired extends ServerErrorDetail {

	public final static SubscriptionRequired DETAIL = new SubscriptionRequired();

	public final static String NAME = "subscription-required";

	private SubscriptionRequired() {
		super(XMLNS_ELEMENT);
	}
}
