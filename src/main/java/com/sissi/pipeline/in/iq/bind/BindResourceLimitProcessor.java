package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Protocol.Type;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.element.ResourceConstraint;

/**
 * @author kim 2014年1月6日
 */
public class BindResourceLimitProcessor extends ProxyProcessor {

	private Integer resources;

	public BindResourceLimitProcessor(Integer resources) {
		super();
		this.resources = resources;
		
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return super.others(context) < this.resources ? true : this.close(context, protocol);
	}

	private Boolean close(JIDContext context, Protocol protocol) {
		context.write(protocol.getParent().clear().reply().setError(new ServerError().setType(Type.CANCEL.toString()).add(ResourceConstraint.DETAIL)));
		context.write(Stream.closeGracefully());
		context.close();
		return false;
	}
}
