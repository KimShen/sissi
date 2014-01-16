package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = PolicyViolation.NAME)
public class PolicyViolation extends ServerErrorDetail {

	public final static PolicyViolation DETAIL_STREAM = new PolicyViolation(XMLNS_STREAM);

	public final static PolicyViolation DETAIL_ELEMENT = new PolicyViolation(XMLNS_ELEMENT);
	
	public final static String NAME = "policy-violation";

	private PolicyViolation() {
	}
	
	private PolicyViolation(String xmlns) {
		super(xmlns);
	}
}
