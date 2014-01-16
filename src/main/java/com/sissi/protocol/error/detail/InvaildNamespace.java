package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月2日
 */
@XmlRootElement(name = InvaildNamespace.NAME)
public class InvaildNamespace extends ServerErrorDetail {

	public final static InvaildNamespace DETAIL = new InvaildNamespace();

	public final static String NAME = "invalid-namespace";

	private InvaildNamespace() {
		super(XMLNS_STREAM);
	}
}