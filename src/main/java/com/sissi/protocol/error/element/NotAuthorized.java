package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2013年12月7日
 */
@XmlType(namespace = ElementErrorDetail.XMLNS)
@XmlRootElement(name = NotAuthorized.NAME)
public class NotAuthorized extends ElementErrorDetail {

	public final static NotAuthorized DETAIL = new NotAuthorized();

	public final static String NAME = "not-authorized";

	public NotAuthorized() {

	}
}
