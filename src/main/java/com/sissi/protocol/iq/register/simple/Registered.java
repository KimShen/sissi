package com.sissi.protocol.iq.register.simple;

import com.sissi.field.Field;
import com.sissi.field.Fields;

/**
 * @author kim 2014年5月8日
 */
public class Registered implements Field<String> {

	public final static Registered FIELD = new Registered();

	public final static String NAME = "registered";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public Fields getChildren() {
		return null;
	}

	@Override
	public boolean hasChild() {
		return false;
	}
}
