package com.sissi.ucenter.muc.impl;

import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;

/**
 * @author kim 2014年3月8日
 */
public class InviteMucConfigArbitrament implements MucConfigArbitrament {

	private final ItemAffiliation affiliation;

	public InviteMucConfigArbitrament(ItemAffiliation affiliation) {
		super();
		this.affiliation = affiliation;
	}

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		return ItemAffiliation.parse(param.relation().affiliation()).contains(this.affiliation);
	}

	@Override
	public String support() {
		return MucConfig.INVITE;
	}
}
