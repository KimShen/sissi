package com.sissi.ucenter.relation.muc.apply.register;

import com.sissi.field.Fields;
import com.sissi.protocol.iq.data.XDataType;
import com.sissi.protocol.iq.data.XField;
import com.sissi.ucenter.relation.muc.apply.ApplySupport;
import com.sissi.ucenter.relation.muc.register.RegisterConfig;

/**
 * @author kim 2014年5月4日
 */
abstract class MongoRegisterApplyContext implements ApplySupport {

	private final String allow;

	public MongoRegisterApplyContext(String allow) {
		super();
		this.allow = allow;
	}

	@Override
	public boolean support(Fields fields) {
		XField form = fields.findField(XDataType.FORM_TYPE.toString(), XField.class);
		XField allow = fields.findField(RegisterConfig.ALLOW.toString(), XField.class);
		return form != null && RegisterConfig.XMLNS.equals(form.getValue().toString()) && allow != null & this.allow.equals(allow.getValue().toString());
	}
}
