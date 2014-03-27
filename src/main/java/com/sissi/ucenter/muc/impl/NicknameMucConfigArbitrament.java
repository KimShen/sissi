package com.sissi.ucenter.muc.impl;

import com.sissi.config.MongoConfig;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;
import com.sissi.ucenter.muc.RelationMuc;

/**
 * @author kim 2014年3月7日
 */
public class NicknameMucConfigArbitrament implements MucConfigArbitrament {

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		Object nick = param.configs().get(MongoConfig.FIELD_NICK);
		return param.activate(true) ? (nick != null ? Boolean.valueOf(nick.toString()) ? !param.relation().cast(RelationMuc.class).name(request.toString(), true) : false : false) : false;
	}

	@Override
	public String support() {
		return MucConfig.NICK;
	}
}
