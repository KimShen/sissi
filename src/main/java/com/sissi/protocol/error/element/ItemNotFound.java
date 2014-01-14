package com.sissi.protocol.error.element;

import javax.xml.bind.annotation.XmlRootElement;

import com.sissi.protocol.error.ElementErrorDetail;

/**
 * @author kim 2014年1月14日
 */
@XmlRootElement(name = ItemNotFound.NAME)
public class ItemNotFound extends ElementErrorDetail {

	public final static ItemNotFound DETAIL = new ItemNotFound();

	public final static String NAME = "item-not-found";

	private ItemNotFound() {

	}
}
