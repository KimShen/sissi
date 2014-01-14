package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = RecipientUnavaliable.NAME)
public class RecipientUnavaliable extends ElementErrorDetail {

	public final static RecipientUnavaliable DETAIL = new RecipientUnavaliable();

	public final static String NAME = "recipient-unavaliable";

	private RecipientUnavaliable() {

	}
}
