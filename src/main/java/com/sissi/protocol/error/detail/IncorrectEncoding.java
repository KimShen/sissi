package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月6日
 */
@XmlRootElement(name = IncorrectEncoding.NAME)
public class IncorrectEncoding extends ServerErrorDetail {

	public final static IncorrectEncoding DETAIL = new IncorrectEncoding();

	public final static String NAME = "incorrect-encoding";

	private IncorrectEncoding() {
		super(XMLNS_ELEMENT);
	}
}
