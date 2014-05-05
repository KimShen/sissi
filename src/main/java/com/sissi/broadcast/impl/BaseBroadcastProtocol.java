package com.sissi.broadcast.impl;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDBuilder;

/**
 * @author kim 2014年1月8日
 */
abstract class BaseBroadcastProtocol {

	protected final JIDBuilder jidBuilder;

	protected final Addressing addressing;

	public BaseBroadcastProtocol(JIDBuilder jidBuilder, Addressing addressing) {
		super();
		this.jidBuilder = jidBuilder;
		this.addressing = addressing;
	}
}
