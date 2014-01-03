package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = PolicyViolation.NAME)
public class PolicyViolation extends StreamErrorDetail {

	public final static PolicyViolation DETAIL = new PolicyViolation();

	public final static String NAME = "policy-violation";

	private PolicyViolation() {

	}
}
