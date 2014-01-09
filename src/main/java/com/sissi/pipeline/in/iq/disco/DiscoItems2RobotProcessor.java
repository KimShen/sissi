package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.bytestreams.BytestreamsProxy;
import com.sissi.protocol.iq.disco.Items;
import com.sissi.protocol.iq.disco.feature.ItemClause;

/**
 * @author kim 2013年12月19日
 */
public class DiscoItems2RobotProcessor extends ProxyProcessor {

	private BytestreamsProxy bytestreamsProxy;

	public DiscoItems2RobotProcessor(BytestreamsProxy bytestreamsProxy) {
		super();
		this.bytestreamsProxy = bytestreamsProxy;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(Items.class.cast(protocol).add(new ItemClause(this.bytestreamsProxy.getJid(), this.bytestreamsProxy.getName())).getParent().reply().setType(Type.RESULT));
		return true;
	}
}
