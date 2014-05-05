package com.sissi.pipeline.in.iq.bytestreams;

import java.util.Comparator;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.iq.bytestreams.Bytestreams;
import com.sissi.protocol.iq.bytestreams.Streamhost;

/**
 * 用户-用户间转发
 * 
 * @author kim 2013年12月18日
 */
public class Bytestreams2FansProcessor extends ProxyProcessor {

	private final Comparator<Streamhost> comparator;

	/**
	 * @param comparator 主机排序
	 */
	public Bytestreams2FansProcessor(Comparator<Streamhost> comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		super.findOne(super.build(protocol.parent().getTo()), true).write(protocol.cast(Bytestreams.class).sort(this.comparator).setFrom(context.jid()).parent().setFrom(context.jid()));
		return true;
	}
}
