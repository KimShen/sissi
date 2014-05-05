package com.sissi.ucenter.relation.muc.register.impl;

import com.sissi.field.Field;
import com.sissi.field.Fields;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XFieldType;
import com.sissi.protocol.iq.data.XInput;
import com.sissi.ucenter.relation.muc.register.RegisterConfig;
import com.sissi.ucenter.relation.muc.register.RegisterContext;

/**
 * 将Register请求转换为可转发格式
 * 
 * @author kim 2014年3月24日
 */
public class FilterRegisterContext implements RegisterContext {

	/**
	 * 表单头
	 */
	private final XInput header = new XInput(XFieldType.HIDDEN.toString(), null, XDataType.FORM_TYPE.toString(), RegisterConfig.XMLNS);

	/**
	 * 是否允许
	 */
	private final XInput allowed = new XInput(XFieldType.BOOLEAN.toString(), null, RegisterConfig.ALLOW.toString(), Boolean.TRUE.toString());

	@Override
	public <T extends Fields> T register(Fields source, T target) {
		target.add(this.header);
		for (Field<?> each : source) {
			// 过滤非Register表单属性
			if (RegisterConfig.contains(each.getName())) {
				target.add(each);
			}
		}
		target.add(this.allowed);
		return target;
	}
}
