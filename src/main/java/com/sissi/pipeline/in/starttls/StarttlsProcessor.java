package com.sissi.pipeline.in.starttls;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.starttls.Failure;
import com.sissi.protocol.starttls.Proceed;

/**
 * <starttls xmlns='urn:ietf:params:xml:ns:xmpp-tls'/>
 * 
 * @author kim 2013年12月17日
 */
public class StarttlsProcessor implements Input {

	@Override
	public boolean input(JIDContext context, Protocol protocol) {
		return context.encrypt() ? this.writeAndTrue(context) : this.writeAndFalse(context);
	}

	private boolean writeAndFalse(JIDContext context) {
		context.write(Failure.FAILURE).write(Stream.closeGraceFully()).close();
		return false;
	}

	private boolean writeAndTrue(JIDContext context) {
		context.write(Proceed.PROCEED);
		return true;
	}
}
