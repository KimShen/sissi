package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月13日
 */
@XmlRootElement(name = ImproperAddressing.NAME)
public class ImproperAddressing extends ServerErrorDetail {

	public final static ImproperAddressing DETAIL = new ImproperAddressing();

	public final static String NAME = "improper-addressing";

	private ImproperAddressing() {
		super(XMLNS_STREAM);
	}
}
