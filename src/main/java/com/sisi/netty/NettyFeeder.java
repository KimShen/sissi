package com.sisi.netty;

import java.util.List;

import com.sisi.context.Context;
import com.sisi.feed.Feeder;
import com.sisi.process.Processor;
import com.sisi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public class NettyFeeder implements Feeder {

	private Context context;

	private List<Processor> processors;

	public NettyFeeder(Context context, List<Processor> processors) {
		super();
		this.processors = processors;
		this.context = context;
	}

	@Override
	public void feed(Protocol protocol) {
		for (Processor processor : processors) {
			this.doEachProcessor(protocol, processor);
		}
	}

	private void doEachProcessor(Protocol protocol, Processor processor) {
		if (processor.isSupport(protocol)) {
			this.context.write(processor.process(this.context, protocol));
		}
	}
}
