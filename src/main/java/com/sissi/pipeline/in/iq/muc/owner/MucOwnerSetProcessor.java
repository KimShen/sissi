package com.sissi.pipeline.in.iq.muc.owner;

import com.sissi.config.MongoConfig;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.muc.Owner;
import com.sissi.ucenter.field.Fields;
import com.sissi.ucenter.field.impl.BeanField;
import com.sissi.ucenter.muc.MucConfigBuilder;

/**
 * @author kim 2014年3月24日
 */
public class MucOwnerSetProcessor extends ProxyProcessor {

	private final MucConfigBuilder mucConfigBuilder;

	public MucOwnerSetProcessor(MucConfigBuilder mucConfigBuilder) {
		super();
		this.mucConfigBuilder = mucConfigBuilder;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		this.mucConfigBuilder.build(super.build(protocol.parent().getTo())).push(Fields.class.cast(protocol.cast(Owner.class).getX().add(new BeanField<Boolean>().setName(MongoConfig.FIELD_ACTIVATE).setValue(true))));
		return true;
	}
}
