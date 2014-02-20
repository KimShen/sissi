package com.sissi.protocol.muc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author kim 2014年2月19日
 */
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement(name = ItemStatus.NAME)
public class ItemStatus {

	public final static ItemStatus STATUS_110 = new ItemStatus("110");
	
	public final static ItemStatus STATUS_210 = new ItemStatus("210");

	public final static String NAME = "status";

	private String code;

	public ItemStatus() {
		super();
	}

	public ItemStatus(String code) {
		super();
		this.code = code;
	}

	@XmlAttribute
	public String getCode() {
		return code;
	}
}
