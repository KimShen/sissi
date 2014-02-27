package com.sissi.protocol.message;

/**
 * @author kim 2014年1月28日
 */
public enum MessageType {

	CHAT, GROUPCHAT, HEADLINE, NORMAL;

	public String toString() {
		return super.toString().toLowerCase();
	}

	public boolean equals(String type) {
		return this == MessageType.parse(type);
	}

	public static MessageType parse(String value) {
		try {
			return MessageType.valueOf(value.toUpperCase());
		} catch (Exception e) {
			return NORMAL;
		}
	}
}
