package com.sisi.relation;

import com.sisi.context.JID;

/**
 * @author kim 2013-11-1
 */
public interface Relation {

	public enum Type {

		FROM, TO, BOTH;

		public String toString() {
			return super.toString().toLowerCase();
		}

		public static Type parse(String value) {
			return Type.valueOf(value.toUpperCase());
		}
	}

	public JID from();

	public JID to();

	public Type type();

	public String alias();

	public String group();

	public String getSubscription();
}
