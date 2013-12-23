package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.bytestreams.BytestreamsProxy;
import com.sissi.protocol.iq.bytestreams.Streamhost;

/**
 * @author kim 2013年12月18日
 */
abstract class Bytestreams2RobotProcessor implements Input {

	private BytestreamsProxy bytestreamsProxy;

	public Bytestreams2RobotProcessor(BytestreamsProxy bytestreamsProxy) {
		super();
		this.bytestreamsProxy = bytestreamsProxy;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(Bytestreams.class.cast(protocol).add(new Streamhost(protocol.getParent().getTo(), this.bytestreamsProxy.getHost(), this.bytestreamsProxy.getPort())).getParent().reply().setType(Type.RESULT));
		return true;
	}
}
