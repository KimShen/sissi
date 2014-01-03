package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = InternalServerError.NAME)
public class InternalServerError extends StreamErrorDetail {

	public final static InternalServerError DETAIL = new InternalServerError();

	public final static String NAME = "internal-server-error";

	private InternalServerError() {

	}
}
