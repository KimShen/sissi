package com.sisi.process.iq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.context.Context;
import com.sisi.process.Processor;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.core.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQProcessor implements Processor {

	private Log log = LogFactory.getLog(this.getClass());

	private Map<String, Processor> forkers = new HashMap<String, Processor>();

	public IQProcessor(List<Forker> forkers) {
		for (Forker forker : forkers) {
			this.log.debug("Add Forker " + forker.fork());
			this.forkers.put(forker.fork(), forker);
		}
	}

	@Override
	public Protocol process(Context context, Protocol protocol) {
		IQ iq = IQ.class.cast(protocol);
		IQ response = (IQ) iq.reply(new IQ());
		response.setType(Protocol.Type.RESULT.toString());
		for (String forker : iq.listChildren()) {
			this.doEachSubProcessor(context, iq, response, forker);
		}
		return response;
	}

	private void doEachSubProcessor(Context context, IQ iq, IQ result, String forker) {
		Processor subProcessor = this.forkers.get(forker);
		this.log.debug("Before forker: " + forker + " / " + subProcessor);
		if (subProcessor != null) {
			this.addSubProtocol(context, iq, result, forker, subProcessor);
		}
	}

	private void addSubProtocol(Context context, IQ iq, IQ result, String forker, Processor subProcessor) {
		Protocol subProtocol = subProcessor.process(context, (Protocol) iq.findChild(forker));
		this.log.debug("After forker: " + forker + " / " + subProtocol);
		if (subProtocol != null) {
			result.add(subProtocol);
		}
	}

	@Override
	public Boolean isSupport(Protocol protocol) {
		return IQ.class.isAssignableFrom(protocol.getClass());
	}
}
