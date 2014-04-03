package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;

/**
 * @author kim 2014年4月3日
 */
public class PublicMucConfigArbitrament implements MucConfigArbitrament {

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		return this.pub(param.configs().get(MongoConfig.FIELD_PUBLIC));
	}

	private boolean pub(Object pub) {
		return pub != null ? Boolean.valueOf(pub.toString()) : false;
	}

	@Override
	public String support() {
		return MucConfig.PUBLIC;
	}
}
