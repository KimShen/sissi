package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.disco.Disco;
import com.sissi.protocol.iq.disco.Item;
import com.sissi.ucenter.Relation;

/**
 * @author kim 2013年12月19日
 */
public class DiscoItemsMucProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Disco disco = protocol.cast(Disco.class);
		for (Relation each : super.myRelations(context.jid())) {
			disco.add(new Item(each.jid(), each.name()));
		}
		context.write(disco.parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
