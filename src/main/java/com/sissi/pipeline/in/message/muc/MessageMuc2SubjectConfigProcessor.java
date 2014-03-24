package com.sissi.pipeline.in.message.muc;

import com.sissi.config.MongoConfig;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.message.Message;
import com.sissi.ucenter.field.impl.BeanField;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年3月13日
 */
public class MessageMuc2SubjectConfigProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	public MessageMuc2SubjectConfigProcessor(MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.mucConfigBuilder.build(super.build(protocol.getTo())).push(new BeanField<Object>().setName(MongoConfig.FIELD_SUBJECT).setValue(protocol.cast(Message.class).getSubject().config()));
		return true;
	}
}
