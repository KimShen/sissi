package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;

/**
 * @author kim 2014年1月2日
 */
public class StreamXmlnsLangProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.setLang(Stream.class.cast(protocol).getLang());
		return true;
	}
}
