package com.sissi.ucenter.relation.muc.room.approve;

import com.sissi.config.Dictionary;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;

/**
 * 房间最大用户校验
 * 
 * @author kim 2014年3月6日
 */
public class MaxUsersConfigApprover implements RoomConfigApprover {

	private final ItemAffiliation affiliation;

	/**
	 * 特权岗位
	 * 
	 * @param affiliation
	 */
	public MaxUsersConfigApprover(String affiliation) {
		super();
		this.affiliation = ItemAffiliation.parse(affiliation);
	}

	@Override
	public boolean approve(RoomConfigParam param, Object request) {
		Object max = param.configs().get(Dictionary.FIELD_MAXUSERS);
		return param.creator() || param.affiliation(this.affiliation.toString()) || (max == null ? true : Integer.valueOf(max.toString()) < Integer.valueOf(request.toString()));
	}

	@Override
	public RoomConfig support() {
		return RoomConfig.MAXUSERS;
	}
}
