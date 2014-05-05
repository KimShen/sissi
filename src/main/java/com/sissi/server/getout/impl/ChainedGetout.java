package com.sissi.server.getout.impl;

import java.util.List;

import com.sissi.context.JIDContext;
import com.sissi.server.getout.Getout;

/**
 * @author kim 2013年11月30日
 */
public class ChainedGetout implements Getout {

	private final List<Getout> getouts;

	public ChainedGetout(List<Getout> getouts) {
		super();
		this.getouts = getouts;
	}

	@Override
	public ChainedGetout getout(JIDContext context) {
		for (Getout each : this.getouts) {
			each.getout(context);
		}
		return this;
	}
}
