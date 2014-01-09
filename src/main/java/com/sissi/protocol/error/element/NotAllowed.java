package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月7日
 */
@XmlRootElement(name = NotAllowed.NAME)
public class NotAllowed extends ElementErrorDetail {

	public final static NotAllowed DETAIL = new NotAllowed();

	public final static String NAME = "not-allowed";

	private NotAllowed() {

	}
}
