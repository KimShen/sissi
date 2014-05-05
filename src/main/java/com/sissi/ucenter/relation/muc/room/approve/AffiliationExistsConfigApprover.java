package com.sissi.ucenter.relation.muc.room.approve;

import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;

/**
 * 岗位限制(是否存在)
 * 
 * @author kim 2014年3月9日
 */
public class AffiliationExistsConfigApprover implements RoomConfigApprover {

	@Override
	public boolean approve(RoomConfigParam param, Object request) {
		return param.configs().get(com.sissi.config.Dictionary.FIELD_AFFILIATION) != null;
	}

	@Override
	public RoomConfig support() {
		return RoomConfig.AFFILIATIONEXISTS;
	}

}
