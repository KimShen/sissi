package com.sisi.process.iq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.context.Context;
import com.sisi.process.Processor;
import com.sisi.protocol.Protocol;
import com.sisi.protocol.Protocol.Type;
import com.sisi.protocol.core.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQProcessor implements Processor {

	private final Log log = LogFactory.getLog(this.getClass());

	private Map<String, Processors> forkers = new HashMap<String, Processors>();

	public IQProcessor(List<Forker> forkers) {
		for (Forker forker : forkers) {
			this.log.debug("Add Forker " + forker.getClass() + " for " + forker.fork() + " / " + forker.type());
			Processors processors = this.forkers.get(forker.fork());
			if (processors == null) {
				processors = new Processors();
			}
			processors.add(forker.type(), forker);
			this.forkers.put(forker.fork(), processors);
		}
	}

	@Override
	public Protocol process(Context context, Protocol protocol) {
		IQ request = IQ.class.cast(protocol);
		IQ response = (IQ) request.reply(new IQ());
		response.setType(Protocol.Type.RESULT.toString());
		if (context.jid() != null) {
			response.setTo(context.jid().asString());
		}
		for (String forker : request.listChildren()) {
			this.log.info("Forker should be process: " + forker);
			this.doEachSubProcessor(context, request, response, forker);
		}
		return response;
	}

	private void doEachSubProcessor(Context context, IQ request, IQ response, String forker) {
		this.log.debug("All forkers: " + this.forkers);
		Processors processors = this.forkers.get(forker);
		if (processors == null) {
			return;
		}

		Type type = Type.parse(request.getType());
		Processor subProcessor = processors.find(type);
		this.log.info("Found subProcessor " + subProcessor.getClass() + " for " + type);
		this.log.debug("Before forker: " + forker + " / " + subProcessor);
		if (subProcessor != null) {
			this.addSubProtocol(context, request, response, forker, subProcessor);
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

	private static class Processors {

		private Map<Type, Processor> forkers = new HashMap<Type, Processor>();

		public void add(Type type, Processor processor) {
			this.forkers.put(type, processor);
		}

		public Processor find(Type type) {
			return this.forkers.get(type);
		}
	}
}
