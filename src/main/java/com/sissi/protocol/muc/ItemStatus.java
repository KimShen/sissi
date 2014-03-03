package com.sissi.protocol.muc;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author kim 2014年2月19日
 */
@XmlType(namespace = XMuc.XMLNS)
@XmlRootElement(name = ItemStatus.NAME)
public class ItemStatus {

	public final static ItemStatus STATUS_100 = new ItemStatus("100");

	public final static ItemStatus STATUS_110 = new ItemStatus("110");

	public final static ItemStatus STATUS_210 = new ItemStatus("210");

	public final static String NAME = "status";

	private static Map<String, ItemStatus> mapping = new HashMap<String, ItemStatus>();

	static {
		mapping.put("100", STATUS_100);
		mapping.put("110", STATUS_110);
		mapping.put("210", STATUS_210);
	}

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
		return this.code;
	}

	public static ItemStatus parse(String code) {
		ItemStatus status = mapping.get(code);
		return status != null ? status : new ItemStatus(code);
	}
}
