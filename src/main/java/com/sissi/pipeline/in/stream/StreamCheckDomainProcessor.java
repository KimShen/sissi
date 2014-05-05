package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.HostUnknown;

/**
 * <stream:stream to='sissi.pw' xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams' version='1.0'></p>To有效性校验
 * 
 * @author kim 2014年1月3日
 */
public class StreamCheckDomainProcessor implements Input {

	private final String localip = "127.0.0.1";

	private final String localhost = "localhost";

	private final String domain;

	public StreamCheckDomainProcessor(String domain) {
		super();
		this.domain = domain;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.to(this.domain, this.localip, this.localhost) ? true : !context.write(Stream.closeWhenOpening(new ServerError().add(HostUnknown.DETAIL)).setFrom(this.domain).setTo(protocol.getFrom())).close();
	}
}
