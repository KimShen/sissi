package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.bytestreams.BytestreamsProxy;
import com.sissi.protocol.iq.bytestreams.Streamhost;

/**
 * 用户-代理
 * 
 * @author kim 2013年12月18日
 */
public class Bytestreams2ProxyProcessor implements Input {

	private final BytestreamsProxy bytestreamsProxy;

	/**
	 * @param bytestreamsProxy 代理
	 */
	public Bytestreams2ProxyProcessor(BytestreamsProxy bytestreamsProxy) {
		super();
		this.bytestreamsProxy = bytestreamsProxy;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.write(protocol.cast(Bytestreams.class).add(new Streamhost(this.bytestreamsProxy)).parent().reply().setType(ProtocolType.RESULT));
		return true;
	}
}
