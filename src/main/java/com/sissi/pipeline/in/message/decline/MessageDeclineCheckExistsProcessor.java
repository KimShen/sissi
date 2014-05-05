package com.sissi.pipeline.in.message.decline;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.ItemNotFound;
import com.sissi.protocol.message.Message;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * Decline.to指定JID存在性校验
 * 
 * @author kim 2014年3月8日
 */
public class MessageDeclineCheckExistsProcessor implements Input {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(ItemNotFound.DETAIL);

	private final VCardContext vcardContext;

	public MessageDeclineCheckExistsProcessor(VCardContext vcardContext) {
		super();
		this.vcardContext = vcardContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.vcardContext.exists(protocol.cast(Message.class).getMuc().getDecline().getTo()) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
