package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.disco.DiscoInfo;
import com.sissi.protocol.iq.disco.Identity;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;

/**
 * MUC保留昵称
 * 
 * @author kim 2014年4月8日
 */
public class DiscoInfoNicknameProcessor extends ProxyProcessor {

	private final RoomBuilder room;

	private final Identity identity;

	public DiscoInfoNicknameProcessor(RoomBuilder room, Identity identity) {
		super();
		this.room = room;
		this.identity = identity;
	}

	/*
	 * 如果存在则使用保留昵称,否则使用JID.resource
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		String reserve = this.room.build(super.build(protocol.parent().getTo())).reserved(context.jid());
		context.write(protocol.cast(DiscoInfo.class).add(identity.clone().setName(reserve != null ? reserve : context.jid().resource())).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
