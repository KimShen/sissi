package com.sissi.pipeline.in.iq.disco;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.disco.Info;
import com.sissi.protocol.iq.disco.feature.Bytestreams;
import com.sissi.protocol.iq.disco.feature.Identity;

/**
 * @author kim 2013年12月19日
 */
public class DiscoInfo2ProxyProcessor extends ProxyProcessor {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(Info.class.cast(protocol).add(new Identity()).add(Bytestreams.FEATURE).getParent().reply().setTo(context.getJid()).setType(Type.RESULT));
		return true;
	}
}
