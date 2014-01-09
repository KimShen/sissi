package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.ErrorDetail;
import com.sissi.protocol.iq.auth.Auth;
import com.sissi.read.MappingMetadata;

/**
 * @author kim 2014年1月6日
 */
@MappingMetadata(uri = Auth.XMLNS, localName = Aborted.NAME)
@XmlRootElement(name = Aborted.NAME)
public class Aborted implements ErrorDetail {

	public final static Aborted DETAIL = new Aborted();

	public final static String NAME = "aborted";

	private Aborted() {

	}

	@Override
	@XmlAttribute
	public String getXmlns() {
		return Auth.XMLNS;
	}
}
