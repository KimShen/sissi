package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.protocol.muc.ItemAffiliation;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;

/**
 * @author kim 2014年3月6日
 */
public class CountMucConfigArbitrament implements MucConfigArbitrament {

	private final ItemAffiliation affiliation;

	public CountMucConfigArbitrament(String affiliation) {
		super();
		this.affiliation = ItemAffiliation.parse(affiliation);
	}

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		Object count = param.configs().get(MongoConfig.FIELD_COUNT);
		return param.creator() || param.level(this.affiliation.toString()) || (count == null ? true : Integer.valueOf(count.toString()) < Integer.valueOf(request.toString()));
	}

	@Override
	public String support() {
		return MucConfig.COUNT;
	}
}
