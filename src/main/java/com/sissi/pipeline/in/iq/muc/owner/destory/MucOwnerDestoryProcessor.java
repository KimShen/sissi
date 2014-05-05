package com.sissi.pipeline.in.iq.muc.owner.destory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.ucenter.relation.muc.room.RoomBuilder;

/**
 * 销毁房间
 * 
 * @author kim 2014年3月24日
 */
public class MucOwnerDestoryProcessor extends ProxyProcessor {

	private final RoomBuilder room;

	public MucOwnerDestoryProcessor(RoomBuilder room) {
		super();
		this.room = room;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.room.build(super.build(protocol.parent().getTo())).destory();
		return true;
	}
}
