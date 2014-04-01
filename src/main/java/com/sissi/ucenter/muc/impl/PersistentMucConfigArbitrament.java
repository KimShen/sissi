package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;

/**
 * @author kim 2014年4月1日
 */
public class PersistentMucConfigArbitrament implements MucConfigArbitrament {

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		return this.persistent(param.configs().get(MongoConfig.FIELD_PERSISTENT));
	}

	private boolean persistent(Object persistent) {
		return persistent != null ? Boolean.valueOf(persistent.toString()) : false;
	}

	@Override
	public String support() {
		return MucConfig.PERSISTENT;
	}

}
