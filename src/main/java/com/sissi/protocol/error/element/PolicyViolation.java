package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlType(namespace = ElementErrorDetail.XMLNS)
@XmlRootElement(name = PolicyViolation.NAME)
public class PolicyViolation extends ElementErrorDetail {

	public final static PolicyViolation DETAIL = new PolicyViolation();

	public final static String NAME = "policy-violation";

	private PolicyViolation() {

	}
}
