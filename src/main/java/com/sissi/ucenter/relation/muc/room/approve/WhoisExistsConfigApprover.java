package com.sissi.ucenter.relation.muc.room.approve;

import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;

/**
 * 是否允许查看真实JID(是否为非匿名房间)校验
 * 
 * @author kim 2014年3月5日
 */
public class WhoisExistsConfigApprover implements RoomConfigApprover {

	@Override
	public boolean approve(RoomConfigParam param, Object request) {
		return param.hidden(false);
	}

	@Override
	public RoomConfig support() {
		return RoomConfig.WHOISEXISTS;
	}

}
