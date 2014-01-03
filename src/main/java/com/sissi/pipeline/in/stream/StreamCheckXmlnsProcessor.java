package com.sissi.pipeline.in.stream;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.error.ServerError;
import com.sissi.protocol.error.stream.InvaildNamespace;
/**
 * @author kim 2014年1月2日
 */
public class StreamCheckXmlnsProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return Stream.class.cast(protocol).isValid() ? true : this.close(context, protocol);
	}

	private Boolean close(JIDContext context, Protocol protocol) {
		context.write(Stream.closeForcible(new ServerError().add(InvaildNamespace.DETAIL)).setFrom(protocol.getTo()).setTo(protocol.getFrom()));
		context.close();
		return false;
	}
}
