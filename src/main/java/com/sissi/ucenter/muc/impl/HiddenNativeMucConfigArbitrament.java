package com.sissi.ucenter.muc.impl;

import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;

/**
 * @author kim 2014年3月5日
 */
public class HiddenNativeMucConfigArbitrament implements MucConfigArbitrament {

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		return param.hidden(false);
	}

	@Override
	public String support() {
		return MucConfig.HIDDEN_NATIVE;
	}

}
