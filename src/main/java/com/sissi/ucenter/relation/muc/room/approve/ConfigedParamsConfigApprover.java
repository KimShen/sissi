package com.sissi.ucenter.relation.muc.room.approve;

import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;

/**
 * 房间已配置校验
 * 
 * @author kim 2014年3月6日
 */
public class ConfigedParamsConfigApprover implements RoomConfigApprover {

	@Override
	public boolean approve(RoomConfigParam param, Object request) {
		return param.activate(false);
	}

	@Override
	public RoomConfig support() {
		return RoomConfig.CONFIGED;
	}
}
