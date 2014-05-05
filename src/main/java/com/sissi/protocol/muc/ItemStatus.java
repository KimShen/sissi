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

	public final static String NAME = "status";

	private static Map<String, ItemStatus> mapping = new HashMap<String, ItemStatus>();

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
		ItemStatus status = ItemStatus.mapping.get(code);
		return status != null ? status : ItemStatus.cached(code);
	}

	private static ItemStatus cached(String code) {
		ItemStatus status = new ItemStatus(code);
		ItemStatus.mapping.put(code, status);
		return status;
	}
}
