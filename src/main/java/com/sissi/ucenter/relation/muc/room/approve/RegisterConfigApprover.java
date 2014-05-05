package com.sissi.ucenter.relation.muc.room.approve;

import com.sissi.config.Dictionary;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;

/**
 * 允许注册校验
 * 
 * @author kim 2014年3月11日
 */
public class RegisterConfigApprover implements RoomConfigApprover {

	@Override
	public boolean approve(RoomConfigParam param, Object request) {
		Object register = param.configs().get(Dictionary.FIELD_REGISTER);
		return register == null ? true : Boolean.valueOf(register.toString());
	}

	@Override
	public RoomConfig support() {
		return RoomConfig.ALLOWREGISTER;
	}
}
