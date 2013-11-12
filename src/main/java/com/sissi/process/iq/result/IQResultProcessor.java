package com.sissi.process.iq.result;

import com.sissi.context.Context;
import com.sissi.process.Processor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQResultProcessor implements Processor {

	@Override
	public void process(Context context, Protocol protocol) {
		context.write(this.prepareIQ(protocol));
	}

	private IQ prepareIQ(Protocol protocol) {
		Protocol iq = protocol.hasParent() ? (IQ) protocol.getParent() : protocol;
		iq.reply().clear();
		iq.setType(Type.RESULT.toString());
		return (IQ)iq;
	}
}
