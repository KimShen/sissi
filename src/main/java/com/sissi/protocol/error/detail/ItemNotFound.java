package com.sissi.protocol.error.detail;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ServerErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = ItemNotFound.NAME)
public class ItemNotFound extends ServerErrorDetail {

	public final static ItemNotFound DETAIL = new ItemNotFound();

	public final static String NAME = "item-not-found";

	private ItemNotFound() {
		super(XMLNS_ELEMENT);
	}
}
