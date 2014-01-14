package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlType(namespace = ElementErrorDetail.XMLNS)
@XmlRootElement(name = Conflict.NAME)
public class Conflict extends ElementErrorDetail {

	public final static Conflict DETAIL = new Conflict();

	public final static String NAME = "conflict";

	private Conflict() {

	}
}
