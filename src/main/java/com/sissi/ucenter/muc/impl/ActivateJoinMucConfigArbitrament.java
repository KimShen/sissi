package com.sissi.ucenter.muc.impl;

import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;

/**
 * @author kim 2014年3月6日
 */
public class ActivateJoinMucConfigArbitrament implements MucConfigArbitrament {

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		return param.activate(true);
	}

	@Override
	public String support() {
		return MucConfig.ACTIVATE_JION;
	}
}
