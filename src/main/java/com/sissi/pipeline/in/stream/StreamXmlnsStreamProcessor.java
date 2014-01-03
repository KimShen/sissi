package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.StreamError;
import com.sissi.protocol.error.detail.InvaildNamespace;
/**
 * @author kim 2014年1月2日
 */
public class StreamXmlnsStreamProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return Stream.class.cast(protocol).isValid() ? true : this.close(context, protocol);
	}

	private Boolean close(JIDContext context, Protocol protocol) {
		context.write(Stream.closeForcible(new StreamError().add(InvaildNamespace.DETAIL)).setFrom(protocol.getTo()).setTo(protocol.getFrom()));
		context.close();
		return false;
	}
}
