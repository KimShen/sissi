package com.sissi.pipeline.process.iq.result;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.ProcessPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQResultProcessor implements ProcessPipeline {

	@Override
	public boolean process(JIDContext context, Protocol protocol) {
		context.write(this.prepareIQ(protocol));
		return false;
	}

	private IQ prepareIQ(Protocol protocol) {
		Protocol iq = protocol.hasParent() ? (IQ) protocol.getParent() : protocol;
		iq.reply().clear();
		iq.setType(Type.RESULT.toString());
		return (IQ)iq;
	}
}
