package com.sissi.ucenter.muc.impl;

import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;

/**
 * @author kim 2014年3月5日
 */
public class AffiliationMucConfigArbitrament implements MucConfigArbitrament {

	private final ItemAffiliation affiliation;

	public AffiliationMucConfigArbitrament(String affiliation) {
		super();
		this.affiliation = ItemAffiliation.parse(affiliation);
	}

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		return this.affiliation.contains(param.relation().getAffiliation());
	}

	@Override
	public String support() {
		return MucConfig.AFFILIATION;
	}

}
