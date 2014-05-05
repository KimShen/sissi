package com.sissi.pipeline.in.presence.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.persistent.Recover;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Element;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.protocol.muc.XMuc;
import com.sissi.protocol.offline.Delay;
import com.sissi.protocol.presence.Presence;
import com.sissi.ucenter.relation.muc.room.Room;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;
import com.sissi.ucenter.relation.muc.room.RoomConfig;

/**
 * 历史消息
 * 
 * @author kim 2014年3月7日
 */
public class PresenceMucJoin2SelfMessageHistoryProcessor extends ProxyProcessor {

	private final Recover recover;

	private final RoomBuilder room;

	public PresenceMucJoin2SelfMessageHistoryProcessor(Recover recover, RoomBuilder room) {
		super();
		this.recover = recover;
		this.room = room;
	}

	/*
	 * 房间如果为Hidden则Delay.From = message.from.bare, 否则为真实JID
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		XMuc x = protocol.cast(Presence.class).findField(XMuc.NAME, XMuc.class);
		if (x.hasHistory()) {
			JID group = super.build(protocol.getTo());
			Room room = this.room.build(group);
			for (Element message : this.recover.pull(group, x.history())) {
				Delay delay = Message.class.cast(message).getDelay();
				delay.setFrom(room.allowed(context.jid(), RoomConfig.WHOISALLOW, super.build(delay.getFrom())) ? super.build(message.getFrom()).asStringWithBare() : delay.getFrom());
				context.write(message);
			}
	}
		return true;
	}
}
