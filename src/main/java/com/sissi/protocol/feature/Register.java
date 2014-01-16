package com.sissi.protocol.feature;

import com.sissi.protocol.Feature;

/**
 * @author kim 2013年12月4日
 */
public class Register extends Feature {

	public final static Register FEATURE = new Register();

	public final static String NAME = "register";

	private final static String XMLNS = "http://jabber.org/features/iq-register";

	private Register() {
		super(XMLNS);
	}
}
