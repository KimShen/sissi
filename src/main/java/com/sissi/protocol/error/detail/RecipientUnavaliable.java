package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = RecipientUnavaliable.NAME)
public class RecipientUnavaliable extends ServerErrorDetail {

	public final static RecipientUnavaliable DETAIL = new RecipientUnavaliable();

	public final static String NAME = "recipient-unavaliable";

	private RecipientUnavaliable() {
		super(XMLNS_ELEMENT);
	}
}
