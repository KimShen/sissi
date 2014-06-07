package com.sissi.pipeline.in;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.commons.Trace;
import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.BadRequest;

/**
 * @author kim 2013-11-14
 */
public class ChainedProcessor implements Input {

	private final static Error error = new ServerError().add(BadRequest.DETAIL);

	private final static Log log = LogFactory.getLog(ChainedProcessor.class);

	private final List<Input> processors;

	protected final boolean next;

	public ChainedProcessor(List<Input> processors) {
		this(false, processors);
	}

	/**
	 * @param next 执行完毕后如果input返回true是否继续执行Pipeline
	 * @param processors
	 */
	public ChainedProcessor(boolean next, List<Input> processors) {
		super();
		this.next = next;
		this.processors = processors;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		try {
			for (Input each : this.processors) {
				if (!each.input(context, protocol)) {
					return false;
				}
			}
		} catch (Exception e) {
			log.warn(e.toString());
			Trace.trace(log, e);
			context.write(Stream.closeWhenRunning(error));
		}
		return this.next;
	}
}
