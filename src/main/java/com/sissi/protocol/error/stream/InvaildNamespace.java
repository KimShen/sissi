package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月2日
 */
@XmlRootElement(name = InvaildNamespace.NAME)
public class InvaildNamespace extends StreamErrorDetail {

	public final static InvaildNamespace DETAIL = new InvaildNamespace();

	public final static String NAME = "invalid-namespace";

	private InvaildNamespace() {

	}
}