package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;

/**
 * @author kim 2014年3月11日
 */
public class RegisterMucConfigArbitrament implements MucConfigArbitrament {

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		Object register = param.configs().get(MongoConfig.FIELD_REGISTER);
		return register == null ? true : Boolean.valueOf(register.toString());
	}

	@Override
	public String support() {
		return MucConfig.REGISTER;
	}
}
