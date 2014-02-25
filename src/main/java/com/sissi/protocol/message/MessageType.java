package com.sissi.protocol.message;

import java.util.HashSet;
import java.util.Set;

/**
 * @author kim 2014年1月28日
 */
public enum MessageType {

	CHAT, GROUPCHAT, HEADLINE, NORMAL;

	private final static Set<String> values = new HashSet<String>();

	static {
		for (MessageType each : MessageType.values()) {
			values.add(each.toString().toUpperCase());
		}
	}

	public String toString() {
		return super.toString().toLowerCase();
	}

	public boolean equals(String type) {
		return this == MessageType.parse(type);
	}

	public static MessageType parse(String value) {
		String type = value != null ? value.toUpperCase() : value;
		return values.contains(type) ? MessageType.valueOf(type) : null;
	}
}
