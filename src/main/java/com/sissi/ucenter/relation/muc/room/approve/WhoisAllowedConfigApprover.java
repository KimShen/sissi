package com.sissi.ucenter.relation.muc.room.approve;

import com.sissi.context.JID;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;

/**
 * 是否允许查看真实JID(是否为创建者或非匿名房间)
 * 
 * @author kim 2014年3月5日
 */
public class WhoisAllowedConfigApprover implements RoomConfigApprover {

	@Override
	public boolean approve(RoomConfigParam param, Object request) {
		return param.hidden(true) && !param.user().like(JID.class.cast(request));
	}

	@Override
	public RoomConfig support() {
		return RoomConfig.WHOISALLOW;
	}
}
