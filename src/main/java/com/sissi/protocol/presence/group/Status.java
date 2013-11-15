package com.sissi.protocol.presence.group;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-11-13
 */
@XmlRootElement
public class Status extends Protocol {

	private Integer code;

	public Status() {
		super();
	}

	public Status(Integer code) {
		super();
		this.code = code;
	}

	@XmlAttribute
	public Integer getCode() {
		return code;
	}
}
