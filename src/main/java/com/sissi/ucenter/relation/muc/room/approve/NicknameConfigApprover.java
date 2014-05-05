package com.sissi.ucenter.relation.muc.room.approve;

import com.sissi.config.Dictionary;
import com.sissi.ucenter.relation.muc.MucRelation;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;

/**
 * 昵称锁定校验
 * 
 * @author kim 2014年3月7日
 */
public class NicknameConfigApprover implements RoomConfigApprover {

	@Override
	public boolean approve(RoomConfigParam param, Object request) {
		Object nick = param.configs().get(Dictionary.FIELD_NICK);
		return param.activate(true) ? (nick != null ? Boolean.valueOf(nick.toString()) ? !param.relation().cast(MucRelation.class).name(request.toString(), true) : false : false) : false;
	}

	@Override
	public RoomConfig support() {
		return RoomConfig.CHANGENICK;
	}
}
