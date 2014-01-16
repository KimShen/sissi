package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月3日
 */
@XmlRootElement(name = UnSupportedStanzaType.NAME)
public class UnSupportedStanzaType extends ServerErrorDetail {

	public final static UnSupportedStanzaType DETAIL = new UnSupportedStanzaType();

	public final static String NAME = "unsupported-stanza-type";

	private UnSupportedStanzaType() {
		super(XMLNS_STREAM);
	}
}
