package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.feature.Starttls;

/**
 * @author kim 2013年12月18日
 */
public class StreamFeatureStarttlsProcessor implements Input {

	/*
	 * 已经启动TLS或已经验证身份则不重复开启
	 * 
	 * @see com.sissi.pipeline.Input#input(com.sissi.context.JIDContext, com.sissi.protocol.Protocol)
	 */
	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.encrypted() || context.auth() ? true : this.writeFeature(protocol.cast(Stream.class));
	}

	private boolean writeFeature(Stream stream) {
		stream.addFeature(Starttls.FEATURE);
		return true;
	}
}
