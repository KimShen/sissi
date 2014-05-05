package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;

/**
 * 初始JIDContext.lang
 * 
 * @author kim 2014年1月2日
 */
public class StreamContextLangProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		context.lang(protocol.cast(Stream.class).getLang());
		return true;
	}
}
