package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = ResourceConstraint.NAME)
public class ResourceConstraint extends ElementErrorDetail {

	public final static ResourceConstraint DETAIL = new ResourceConstraint();

	public final static String NAME = "resource-constraint";

	private ResourceConstraint() {

	}
}
