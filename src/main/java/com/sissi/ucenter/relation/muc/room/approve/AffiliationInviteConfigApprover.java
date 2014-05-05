package com.sissi.ucenter.relation.muc.room.approve;

import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.relation.muc.room.RoomConfig;
import com.sissi.ucenter.relation.muc.room.RoomConfigApprover;
import com.sissi.ucenter.relation.muc.room.RoomConfigParam;

/**
 * 允许邀请校验
 * 
 * @author kim 2014年3月8日
 */
public class AffiliationInviteConfigApprover implements RoomConfigApprover {

	private final ItemAffiliation affiliation;

	public AffiliationInviteConfigApprover(String affiliation) {
		super();
		this.affiliation = ItemAffiliation.parse(affiliation);
	}

	@Override
	public boolean approve(RoomConfigParam param, Object request) {
		return ItemAffiliation.parse(param.relation().affiliation()).contains(this.affiliation);
	}

	@Override
	public RoomConfig support() {
		return RoomConfig.ALLOWINVITES;
	}
}
