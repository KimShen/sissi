package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = ResourceConstraint.NAME)
public class ResourceConstraint extends ServerErrorDetail {

	public final static ResourceConstraint DETAIL_STREAM = new ResourceConstraint(XMLNS_STREAM);

	public final static ResourceConstraint DETAIL_ELEMENT = new ResourceConstraint(XMLNS_ELEMENT);

	public final static String NAME = "resource-constraint";

	private ResourceConstraint() {
	}

	private ResourceConstraint(String xmlns) {
		super(xmlns);
	}
}
