package com.sissi.ucenter.relation.muc.room.approve;

import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;

/**
 * 更新主题校验
 * 
 * @author kim 2014年3月13日
 */
public class SubjectConfigApprover implements RoomConfigApprover {

	private final ItemRole role;

	public SubjectConfigApprover(String role) {
		super();
		this.role = ItemRole.parse(role);
	}

	@Override
	public boolean approve(RoomConfigParam param, Object request) {
		return param.creator() || ItemRole.parse(param.relation().role()).contains(this.role);
	}

	@Override
	public RoomConfig support() {
		return RoomConfig.CHANGESUBJECT;
	}
}
