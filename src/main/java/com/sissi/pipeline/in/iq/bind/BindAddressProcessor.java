package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-29
 */
public class BindAddressProcessor extends UtilProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		this.addressing.ban(context);
		this.addressing.join(context);
		context.setBinding(true);
		return true;
	}
}
