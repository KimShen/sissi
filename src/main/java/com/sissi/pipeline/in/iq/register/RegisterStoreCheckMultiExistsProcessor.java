package com.sissi.pipeline.in.iq.register;

import com.sissi.context.JIDBuilder;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Conflict;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.data.XField;
import com.sissi.protocol.iq.register.Register;
import com.sissi.protocol.iq.register.simple.Username;
import com.sissi.ucenter.vcard.VCardContext;

/**
 * 校验当前用户是否已登录
 * 
 * @author kim 2014年5月8日
 */
public class RegisterStoreCheckMultiExistsProcessor implements Input {

	private final Error error = new ServerError().type(ProtocolType.CANCEL).add(Conflict.DETAIL_ELEMENT);

	private final VCardContext vcardContext;

	private final JIDBuilder jidBuilder;

	public RegisterStoreCheckMultiExistsProcessor(VCardContext vcardContext, JIDBuilder jidBuilder) {
		super();
		this.vcardContext = vcardContext;
		this.jidBuilder = jidBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.vcardContext.exists(this.jidBuilder.build(protocol.cast(Register.class).findField(XData.NAME, XData.class).findField(Username.NAME, XField.class).getValue().toString(), null)) ? this.writeAndReturn(context, protocol) : true;
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
