package com.sissi.pipeline.in.iq.muc.owner;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.IQ;
import com.sissi.protocol.iq.data.XData;
import com.sissi.protocol.muc.Owner;

/**
 * 表单列表
 * 
 * @author kim 2014年3月24日
 */
public class MucOwnerGetProcessor extends ProxyProcessor {

	private final Owner owner;

	public MucOwnerGetProcessor(XData data) {
		super();
		this.owner = new Owner(data);
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().cast(IQ.class).clear().add(this.owner).setType(ProtocolType.RESULT).reply());
		return true;
	}
}
