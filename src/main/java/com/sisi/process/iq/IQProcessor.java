package com.sisi.process.iq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sisi.context.Context;
import com.sisi.process.Processor;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.core.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQProcessor implements Processor {

	private Map<String, Processor> forkers = new HashMap<String, Processor>();

	public IQProcessor(List<Forker> forkers) {
		for (Forker forker : forkers) {
			this.forkers.put(forker.forkName(), forker);
		}
	}

	@Override
	public Protocol process(Context context, Protocol protocol) {
		IQ iq = IQ.class.cast(protocol);
		IQ result = new IQ();
		iq.reply(result);
		result.setType("result");
		for (String forker : iq.listChildren()) {
			this.doEachSubProcessor(context, iq, result, forker);
		}
		return result;
	}

	private void doEachSubProcessor(Context context, IQ iq, IQ result, String forker) {
		Processor subProcessor = this.forkers.get(forker);
		if (subProcessor != null) {
			this.addSubProtocol(context, iq, result, forker, subProcessor);
		}
	}

	private void addSubProtocol(Context context, IQ iq, IQ result, String forker, Processor subProcessor) {
		Protocol subProtocol = subProcessor.process(context, (Protocol) iq.findChild(forker));
		if (subProtocol != null) {
			result.add(subProtocol);
		}
	}

	@Override
	public Boolean isSupport(Protocol protocol) {
		return IQ.class.isAssignableFrom(protocol.getClass());
	}
}
