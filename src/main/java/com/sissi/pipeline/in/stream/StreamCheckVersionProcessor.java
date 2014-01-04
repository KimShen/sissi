package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.stream.UnSupportedVersion;

/**
 * @author kim 2014年1月4日
 */
public class StreamCheckVersionProcessor implements Input {

	private final String minVersion;

	public StreamCheckVersionProcessor(String minVersion) {
		super();
		this.minVersion = minVersion;
	}

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return this.minVersion.compareTo(Stream.class.cast(protocol).getVersion()) <= 0 ? true : this.close(context, protocol);
	}

	private Boolean close(JIDContext context, Protocol protocol) {
		context.write(Stream.closeForcible(new ServerError().add(UnSupportedVersion.DETAIL)).setFrom(protocol.getTo()).setTo(protocol.getFrom()));
		context.close();
		return false;
	}
}
