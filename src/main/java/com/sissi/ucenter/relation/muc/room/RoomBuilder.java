package com.sissi.ucenter.relation.muc.room;

import com.sissi.context.JID;

/**
 * @author kim 2014年2月20日
 */
public interface RoomBuilder {

	public Room build(JID group);
}
