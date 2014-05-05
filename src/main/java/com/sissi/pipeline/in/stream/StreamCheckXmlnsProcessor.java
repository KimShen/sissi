package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.InvaildNamespace;

/**
 * <stream:stream to='sissi.pw' xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams' version='1.0'></p>Xmlns校验
 * 
 * @author kim 2014年1月2日
 */
public class StreamCheckXmlnsProcessor implements Input {

	private final String domain;

	public StreamCheckXmlnsProcessor(String domain) {
		super();
		this.domain = domain;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return protocol.cast(Stream.class).xmlns() ? true : !context.write(Stream.closeWhenOpening(new ServerError().add(InvaildNamespace.DETAIL)).setFrom(this.domain).setTo(protocol.getFrom())).close();
	}
}
