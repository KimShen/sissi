package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.disco.Disco;
import com.sissi.protocol.iq.disco.Item;

/**
 * @author kim 2013年12月19日
 */
public class DiscoFixedItemsProcessor extends ProxyProcessor {

	private final Item[] items;

	public DiscoFixedItemsProcessor(Item[] items) {
		super();
		this.items = items;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Disco.class).add(this.items).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
