package com.sissi.pipeline.in.message;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.UtilProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.error.Error;
import com.sissi.protocol.error.detail.Blocked;
import com.sissi.protocol.error.detail.NotAcceptable;
import com.sissi.protocol.message.Message;
import com.sissi.ucenter.BanContext;

/**
 * @author kim 2013年12月7日
 */
public class MessageBlockedProcessor extends UtilProcessor {

	private BanContext banContext;

	public MessageBlockedProcessor(BanContext banContext) {
		super();
		this.banContext = banContext;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.banContext.isBan(context.getJid(), super.jidBuilder.build(protocol.getTo())) ? this.writeAndReturn(context, protocol) : true;
	}

	private Boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(Message.class.cast(protocol).setFrom(protocol.getTo()).setTo((String)null).setError(new Error().setType(Type.CANCEL).add(NotAcceptable.DETAIL).add(Blocked.DETAIL)));
		return false;
	}
}
