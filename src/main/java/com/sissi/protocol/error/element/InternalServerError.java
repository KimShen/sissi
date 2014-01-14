package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlType(namespace = ElementErrorDetail.XMLNS)
@XmlRootElement(name = InternalServerError.NAME)
public class InternalServerError extends ElementErrorDetail {

	public final static InternalServerError DETAIL = new InternalServerError();

	public final static String NAME = "internal-server-error";

	private InternalServerError() {

	}
}
