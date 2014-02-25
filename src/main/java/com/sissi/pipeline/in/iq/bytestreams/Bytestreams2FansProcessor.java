package com.sissi.pipeline.in.iq.bytestreams;

import java.util.Comparator;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.bytestreams.Streamhost;

/**
 * @author kim 2013年12月18日
 */
public class Bytestreams2FansProcessor extends ProxyProcessor {

	private final Comparator<Streamhost> comparator;

	public Bytestreams2FansProcessor(Comparator<Streamhost> comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.findOne(super.build(protocol.getParent().getTo()), true).write(protocol.cast(Bytestreams.class).sort(this.comparator).setFrom(context.jid()).getParent().setFrom(context.jid()));
		return true;
	}
}
