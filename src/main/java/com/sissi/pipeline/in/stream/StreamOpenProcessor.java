package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;

/**
 * @author kim 2013年12月18日
 */
public class StreamOpenProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(Stream.class.cast(protocol).setLang(context.getLang()).reply().setFrom(context.getDomain()));
		return true;
	}
}
