package com.sissi.pipeline.in.iq.session;

import com.sissi.context.JIDContext;
import com.sissi.offline.DelayElementBox;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Element;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-29
 */
public class Session4SelfsProcessor implements Input {

	private DelayElementBox delayElementBox;

	public Session4SelfsProcessor(DelayElementBox delayElementBox) {
		super();
		this.delayElementBox = delayElementBox;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		for (Element each : delayElementBox.fetch(context.getJid().getBare())) {
			context.write(each);
		}
		return true;
	}
}
