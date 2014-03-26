package com.sissi.ucenter.muc.impl;

import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XFieldType;
import com.sissi.protocol.iq.data.XInput;
import com.sissi.protocol.muc.OwnerConfig;
import com.sissi.protocol.muc.XMuc;
import com.sissi.ucenter.field.Field;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.muc.MucRegister;

/**
 * @author kim 2014年3月24日
 */
public class PairedMucRegister implements MucRegister {

	private final XInput header = new XInput(XFieldType.HIDDEN.toString(), null, XDataType.FORM_TYPE.toString(), XMuc.XMLNS + "#register");

	private final XInput allowed = new XInput(XFieldType.BOOLEAN.toString(), null, OwnerConfig.REGISTER_ALLOW.toString(), "0");

	@Override
	public <T extends Fields> T register(Fields source, T target) {
		target.add(this.header);
		for (Field<?> each : source) {
			if (OwnerConfig.contains(each.getName())) {
				target.add(each);
			}
		}
		target.add(this.allowed);
		return target;
	}
}
