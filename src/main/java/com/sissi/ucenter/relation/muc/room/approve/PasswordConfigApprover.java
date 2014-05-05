package com.sissi.ucenter.relation.muc.room.approve;

import com.sissi.config.Dictionary;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;

/**
 * 密码校验
 * 
 * @author kim 2014年3月5日
 */
public class PasswordConfigApprover implements RoomConfigApprover {

	@Override
	public boolean approve(RoomConfigParam param, Object request) {
		return param.creator() ? true : this.password(param.configs().get(Dictionary.FIELD_PASSWORD), request);
	}

	private boolean password(Object pass, Object request) {
		return pass != null ? pass.equals(request) : true;
	}

	@Override
	public RoomConfig support() {
		return RoomConfig.ROOMSECRET;
	}
}
