package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.disco.Disco;
import com.sissi.protocol.iq.disco.Item;

/**
 * @author kim 2013年12月19日
 */
public class DiscoItems4RoomMucProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Disco disco = protocol.cast(Disco.class);
		for (JID each : super.whoSubscribedMe(super.build(protocol.parent().getTo()))) {
			disco.add(new Item(each.asString()));
		}
		context.write(disco.parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
