package com.sissi.ucenter.relation.muc.room.approve;

import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;

/**
 * 岗位限制校验(是否越权)
 * 
 * @author kim 2014年3月5日
 */
public class AffiliationAllowedConfigApprover implements RoomConfigApprover {

	@Override
	public boolean approve(RoomConfigParam param, Object request) {
		return param.affiliation();
	}

	@Override
	public RoomConfig support() {
		return RoomConfig.AFFILIATIONALLOW;
	}

}
