package com.sissi.pipeline.in.iq.bytestreams;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.bytestreams.Streamhost;

/**
 * @author kim 2013年12月18日
 */
/**
 * @author kim 2013年12月18日
 */
public class BytestreamsProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(Bytestreams.class.cast(protocol).setStreamhost(new Streamhost(protocol.getParent().getTo(), "127.0.0.1", "1080")).getParent().reply().setType(Type.RESULT));
		return null;
	}
}
