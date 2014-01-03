package com.sissi.protocol.error;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Error;

/**
 * @author kim 2013年12月4日
 */
@XmlRootElement(name = ElementlError.NAME)
public class ElementlError extends ServerError {

	public ElementlError() {
		super();
	}

	public ElementlError(Error error) {
		super(error);
	}
}
