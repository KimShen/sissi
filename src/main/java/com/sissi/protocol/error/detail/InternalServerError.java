package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = InternalServerError.NAME)
public class InternalServerError extends ServerErrorDetail {

	public final static InternalServerError DETAIL_STREAM = new InternalServerError(XMLNS_STREAM);

	public final static InternalServerError DETAIL_ELEMENT = new InternalServerError(XMLNS_ELEMENT);
	
	public final static String NAME = "internal-server-error";

	private InternalServerError() {
	}
	
	private InternalServerError(String xmlns) {
		super(xmlns);
	}
}
