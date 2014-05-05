package com.sissi.pipeline.in.iq.register.muc;

import com.sissi.context.JIDContext;
import com.sissi.field.impl.BeanFields;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.iq.register.Register;
import com.sissi.ucenter.relation.muc.apply.ApplyContext;

/**
 * 注册表单持久化
 * 
 * @author kim 2013年12月5日
 */
public class RegisterMucStoreProcessor extends ProxyProcessor {

	private final ApplyContext applyContext;

	public RegisterMucStoreProcessor(ApplyContext applyContext) {
		super();
		this.applyContext = applyContext;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return this.applyContext.apply(context.jid(), super.build(protocol.parent().getTo()), new BeanFields(false, protocol.cast(Register.class).findField(XData.NAME, XData.class).getFields()));
	}
}
