package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = ResourceConstraint.NAME)
public class ResourceConstraint extends StreamErrorDetail {

	public final static ResourceConstraint DETAIL = new ResourceConstraint();

	public final static String NAME = "resource-constraint";

	private ResourceConstraint() {

	}
}
