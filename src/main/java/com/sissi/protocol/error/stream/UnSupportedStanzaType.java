package com.sissi.protocol.error.stream;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.StreamErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = UnSupportedStanzaType.NAME)
public class UnSupportedStanzaType extends StreamErrorDetail {

	public final static UnSupportedStanzaType DETAIL = new UnSupportedStanzaType();

	public final static String NAME = "unsupported-stanza-type";

	private UnSupportedStanzaType() {

	}
}
