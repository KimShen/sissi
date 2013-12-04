package com.sissi.protocol.error;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement(name = "error")
public class Error extends Protocol {

	private String code;

	public Error() {
		super();
	}

	public Error(String code, Type type) {
		super();
		super.setType(type);
		this.code = code;
	}

	@XmlAttribute
	public String getCode() {
		return code;
	}
}
