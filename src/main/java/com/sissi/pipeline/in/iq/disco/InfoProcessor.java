package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.disco.Blocking;
import com.sissi.protocol.iq.disco.Info;

/**
 * @author kim 2013年12月5日
 */
public class InfoProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(Info.class.cast(protocol).add(Blocking.FEATURE).getParent().reply().setTo(context.getJid().getBare()).setType(Type.RESULT));
		return true;
	}
}
