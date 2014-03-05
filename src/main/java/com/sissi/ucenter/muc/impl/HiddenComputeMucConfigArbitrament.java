package com.sissi.ucenter.muc.impl;

import com.sissi.context.JID;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;

/**
 * @author kim 2014年3月5日
 */
public class HiddenComputeMucConfigArbitrament implements MucConfigArbitrament {

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		return param.hidden(true) && !param.user().like(JID.class.cast(request));
	}

	@Override
	public String support() {
		return MucConfig.HIDDEN_COMPUTER;
	}
}
