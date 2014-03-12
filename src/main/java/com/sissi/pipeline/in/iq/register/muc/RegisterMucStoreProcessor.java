package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.config.MongoConfig;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.register.Register;
import com.sissi.ucenter.field.impl.BeanField;
import com.sissi.ucenter.field.impl.BeanFields;
import com.sissi.ucenter.muc.MucApplyContext;

/**
 * @author kim 2013年12月5日
 */
public class RegisterMucStoreProcessor implements Input {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(BadRequest.DETAIL);

	private final MucApplyContext mucApplyContext;

	public RegisterMucStoreProcessor(MucApplyContext mucApplyContext) {
		super();
		this.mucApplyContext = mucApplyContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.mucApplyContext.apply(new BeanFields(false, protocol.cast(Register.class).findField(XData.NAME, XData.class).getFields()).add(new BeanField<String>().setName(MongoConfig.FIELD_FROM).setValue(context.jid().asString())).add(new BeanField<String>().setName(MongoConfig.FIELD_TO).setValue(protocol.getTo()))) ? true : this.writeAndReturn(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
