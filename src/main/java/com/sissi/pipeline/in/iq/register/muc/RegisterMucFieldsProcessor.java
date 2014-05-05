package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.context.JIDContext;
import com.sissi.field.Fields;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.register.Register;

/**
 * MUC注册表单初始化
 * 
 * @author kim 2013年12月3日
 */
public class RegisterMucFieldsProcessor implements Input {

	private final Fields fields;

	public RegisterMucFieldsProcessor(Fields fields) {
		super();
		this.fields = fields;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Register.class).add(this.fields).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
