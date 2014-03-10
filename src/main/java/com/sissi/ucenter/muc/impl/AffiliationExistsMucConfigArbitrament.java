package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;

/**
 * @author kim 2014年3月9日
 */
public class AffiliationExistsMucConfigArbitrament implements MucConfigArbitrament {

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		return param.configs().get(MongoConfig.FIELD_AFFILIATION) != null;
	}

	@Override
	public String support() {
		return MucConfig.AFFILIATION_EXISTS;
	}

}
