package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.disco.Items;
import com.sissi.protocol.iq.disco.feature.ItemClause;

/**
 * @author kim 2013年12月18日
 */
public class DiscoItemsProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(Items.class.cast(protocol).add(new ItemClause("proxy.192.168.1.113", "Socks 5 Bytestreams Proxy")).getParent().reply().setType(Type.RESULT));
		return true;
	}
}