package com.sissi.protocol.error;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.Error;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = ElementlError.NAME)
public class StreamError extends ServerError {

	public StreamError() {
		super();
	}

	public StreamError(Error error) {
		super(error);
	}
}
