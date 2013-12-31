package com.sissi.protocol.presence;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author kim 2013年12月30日
 */
@XmlRootElement
public class XUserStatus {

	public final static XUserStatus STATUS_101 = new XUserStatus("110");

	public final static String NAME = "status";

	private String code;

	public XUserStatus() {
		super();
	}

	public XUserStatus(String code) {
		super();
		this.code = code;
	}

	@XmlAttribute
	public String getCode() {
		return code;
	}
}
