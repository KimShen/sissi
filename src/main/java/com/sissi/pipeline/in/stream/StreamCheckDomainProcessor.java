package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.HostUnknown;

/**
 * @author kim 2014年1月3日
 */
public class StreamCheckDomainProcessor implements Input {

	private final String localhost = "127.0.0.1";

	private final String domain;

	public StreamCheckDomainProcessor(String domain) {
		super();
		this.domain = domain;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.domain.equals(protocol.getTo()) || this.localhost.equals(protocol.getTo()) ? true : this.close(context, protocol);
	}

	private Boolean close(JIDContext context, Protocol protocol) {
		return !context.write(Stream.closeWhenOpening(new ServerError().add(HostUnknown.DETAIL)).setFrom(this.domain).setTo(protocol.getFrom())).close();
	}
}
