package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.disco.Blocking;
import com.sissi.protocol.iq.disco.DiscoInfo;

/**
 * @author kim 2013年12月5日
 */
public class DiscoInfoProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(DiscoInfo.class.cast(protocol).add(Blocking.FEATURE).getParent().reply().setTo(context.getJid()).setType(Type.RESULT));
		return true;
	}
}
