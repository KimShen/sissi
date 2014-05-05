package com.sissi.protocol.muc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kim 2014年4月15日
 */
public enum FormType {

	MUC_ROOMINFO;

	private final static String prefix = "http://jabber.org/protocol/";

	private final static Map<FormType, String> mapping = new HashMap<FormType, String>();

	static {
		mapping.put(MUC_ROOMINFO, "muc#roominfo");
	}

	public String toString() {
		return FormType.prefix + FormType.mapping.get(this);
	}
}
