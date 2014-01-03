package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.stream.HostUnknown;

/**
 * @author kim 2014年1月3日
 */
public class StreamCheckToProcessor implements Input {

	private final String host;

	public StreamCheckToProcessor(String host) {
		super();
		this.host = host;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.host.equals(protocol.getTo()) ? true : this.close(context, protocol);
	}

	private Boolean close(JIDContext context, Protocol protocol) {
		context.write(Stream.closeForcible(new ServerError().add(HostUnknown.DETAIL)).setFrom(this.host).setTo(protocol.getFrom()));
		context.close();
		return false;
	}
}
