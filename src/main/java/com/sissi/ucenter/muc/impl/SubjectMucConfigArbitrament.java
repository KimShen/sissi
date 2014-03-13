package com.sissi.ucenter.muc.impl;

import com.sissi.protocol.muc.ItemRole;
import com.sissi.ucenter.muc.MucConfig;
import com.sissi.ucenter.muc.MucConfigArbitrament;
import com.sissi.ucenter.muc.MucConfigParam;

/**
 * @author kim 2014年3月13日
 */
public class SubjectMucConfigArbitrament implements MucConfigArbitrament {

	@Override
	public boolean arbitrate(MucConfigParam param, Object request) {
		return param.creator() || ItemRole.parse(param.relation().role()).contains(ItemRole.MODERATOR);
	}

	@Override
	public String support() {
		return MucConfig.SUBJECT;
	}
}
