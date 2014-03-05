package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;

/**
 * @author kim 2014年3月5日
 */
public class PasswordMucConfigArbitrament implements MucConfigArbitrament {

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		return param.creator() ? true : this.password(param.configs().get(MongoConfig.FIELD_PASSWORD), request);
	}

	private boolean password(Object pass, Object request) {
		return pass != null ? pass.equals(request) : true;
	}

	@Override
	public String support() {
		return MucConfig.PASSWORD;
	}
}
