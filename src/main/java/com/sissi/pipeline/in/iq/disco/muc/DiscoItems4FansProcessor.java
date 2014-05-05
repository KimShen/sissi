package com.sissi.pipeline.in.iq.disco.muc;

import com.sissi.context.JID;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.disco.Disco;
import com.sissi.protocol.iq.disco.Item;

/**
 * To对应JID在线MUC房间集合
 * 
 * @author kim 2013年12月19日
 */
public class DiscoItems4FansProcessor extends ProxyProcessor {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Disco disco = protocol.cast(Disco.class);
		// 如果一个用户以不同资源进入房间则仅显示其中一个
		for (JID each : super.iSubscribedWho((super.build(protocol.parent().getTo())))) {
			disco.add(new Item(each.asStringWithBare(), each.resource()));
		}
		context.write(disco.parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
