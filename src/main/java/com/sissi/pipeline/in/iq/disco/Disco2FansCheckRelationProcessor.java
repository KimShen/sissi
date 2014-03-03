package com.sissi.pipeline.in.iq.disco;

import java.util.Set;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.CheckRelationProcessor;
import com.sissi.protocol.Error;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.ProtocolType;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.Forbidden;

/**
 * @author kim 2014年1月26日
 */
public class Disco2FansCheckRelationProcessor extends CheckRelationProcessor {

	private final Error error = new ServerError().setType(ProtocolType.CANCEL).add(Forbidden.DETAIL);

	private final Set<String> domains;

	public Disco2FansCheckRelationProcessor(Set<String> domains, boolean free) {
		super(free);
		this.domains = domains;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return !protocol.to() || protocol.to(this.domains) || super.ourRelation(context, protocol) ? true : this.writeAndReturn(context, protocol);
	}

	protected boolean writeAndReturn(JIDContext context, Protocol protocol) {
		context.write(protocol.parent().reply().setError(this.error));
		return false;
	}
}
