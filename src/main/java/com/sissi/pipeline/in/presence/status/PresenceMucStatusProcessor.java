package com.sissi.pipeline.in.presence.status;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.presence.Presence;

/**
 * @author kim 2014年2月18日
 */
public class PresenceMucStatusProcessor extends ProxyProcessor {

	private final Input input;

	public PresenceMucStatusProcessor(Input input) {
		super();
		this.input = input;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		if (context.presence()) {
			Presence presence = new Presence().clauses(protocol.cast(Presence.class).clauses());
			for (JID group : super.iSubscribedWho(context.jid())) {
				this.input.input(context, presence.setTo(group));
			}
		}
		return true;
	}
}
