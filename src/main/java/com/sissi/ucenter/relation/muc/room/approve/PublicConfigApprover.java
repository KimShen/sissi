package com.sissi.ucenter.relation.muc.room.approve;

import com.sissi.config.Dictionary;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;

/**
 * 公开房间校验
 * 
 * @author kim 2014年4月3日
 */
public class PublicConfigApprover implements RoomConfigApprover {

	@Override
	public boolean approve(RoomConfigParam param, Object request) {
		return this.pub(param.configs().get(Dictionary.FIELD_PUBLIC));
	}

	private boolean pub(Object pub) {
		return pub != null ? Boolean.valueOf(pub.toString()) : false;
	}

	@Override
	public RoomConfig support() {
		return RoomConfig.PUBLICROOM;
	}
}
