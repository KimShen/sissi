package com.sissi.ucenter.muc.impl;

import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.muc.OwnerConfig;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.muc.MucConfigReviewer;
import com.sissi.ucenter.muc.MucStatus;

/**
 * @author kim 2014年3月27日
 */
public class HiddenMucConfigReviewer implements MucConfigReviewer {

	@Override
	public MucStatus review(Fields fields, MucStatus status) {
		XField hidden = fields.findField(OwnerConfig.ROOMCONFIG_WHOIS.toString(), XField.class);
		return hidden != null && hidden.getValue() != null && Boolean.valueOf(hidden.getValue().toString()) == true ? status.add("174") : status.add("172");
	}
}
