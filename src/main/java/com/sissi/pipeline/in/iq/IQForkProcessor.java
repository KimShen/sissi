package com.sissi.pipeline.in.iq;

import java.util.Set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.pipeline.InputFinder;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;
import com.sissi.protocol.iq.IQ;

/**
 * @author kim 2013-10-24
 */
public class IQForkProcessor implements Input {

	private final Error error = new ServerError().setType(ProtocolType.AUTH).add(Forbidden.DETAIL);

	private final Set<Class<? extends Protocol>> ignores;

	private final InputFinder finder;

	private final Input noChild;

	/**
	 * @param ignores 忽略context.auth的IQ子节
	 * @param finder
	 * @param noChild 如果不存在匹配
	 */
	public IQForkProcessor(Set<Class<? extends Protocol>> ignores, InputFinder finder, Input noChild) {
		super();
		this.finder = finder;
		this.noChild = noChild;
		this.ignores = ignores;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		for (Protocol sub : protocol.cast(IQ.class).listChildren()) {
			return (context.auth() || this.ignores.contains(sub.getClass())) ? this.finder.find(sub).input(context, sub) : this.writeAndReturn(context, protocol);
		}
		return this.noChild.input(context, protocol);
	}

	private boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
