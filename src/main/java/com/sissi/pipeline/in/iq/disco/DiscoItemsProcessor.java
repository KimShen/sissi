package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.bytestreams.BytestreamsProxy;
import com.sissi.protocol.iq.disco.Disco;
import com.sissi.protocol.iq.disco.Item;

/**
 * @author kim 2013年12月19日
 */
public class DiscoItemsProcessor extends ProxyProcessor {

	private final Item item;

	public DiscoItemsProcessor(BytestreamsProxy bytestreamsProxy) {
		super();
		this.item = new Item(bytestreamsProxy);
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(Disco.class.cast(protocol).add(this.item).getParent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
