package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;

/**
 * @author kim 2013年12月3日
 */
public class StreamOpenConsumeProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		Stream.class.cast(protocol).consume();
		return true;
	}

}
