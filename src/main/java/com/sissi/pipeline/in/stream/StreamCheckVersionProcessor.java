package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.detail.UnSupportedVersion;

/**
 * @author kim 2014年1月4日
 */
public class StreamCheckVersionProcessor implements Input {

	private final String minVersion;

	private final String domain;

	public StreamCheckVersionProcessor(String domain, String minVersion) {
		super();
		this.domain = domain;
		this.minVersion = minVersion;
	}

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return Stream.class.cast(protocol).version(this.minVersion) ? true : !context.write(Stream.closeWhenOpening(new ServerError().add(UnSupportedVersion.DETAIL)).setFrom(this.domain).setTo(protocol.getFrom())).close();
	}
}
