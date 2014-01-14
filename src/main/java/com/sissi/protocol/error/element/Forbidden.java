package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = Forbidden.NAME)
public class Forbidden extends ElementErrorDetail {

	public final static Forbidden DETAIL = new Forbidden();

	public final static String NAME = "forbidden";

	private Forbidden() {

	}
}
