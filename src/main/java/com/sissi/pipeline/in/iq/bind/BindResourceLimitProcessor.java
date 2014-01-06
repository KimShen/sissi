package com.sissi.pipeline.in.iq.bind;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.in.ProxyProcessor;
import com.sissi.protocol.Protocol;

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
		return super.others(context) < this.resources ? true : this.close(context);
	}

	private Boolean close(JIDContext context) {
		return context.close();
	}
}
