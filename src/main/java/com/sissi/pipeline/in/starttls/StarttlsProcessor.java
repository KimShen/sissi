package com.sissi.pipeline.in.starttls;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.Stream;
import com.sissi.protocol.starttls.Failure;
import com.sissi.protocol.starttls.Proceed;

/**
 * @author kim 2013年12月17日
 */
public class StarttlsProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		return context.startTls() ? this.writeAndTrue(context) : this.writeAndFalse(context);
	}

	private Boolean writeAndFalse(JIDContext context) {
		context.write(Failure.FAILURE).write(Stream.closeGracefully()).close();
		return false;
	}

	private Boolean writeAndTrue(JIDContext context) {
		context.write(Proceed.PROCEED);
		return true;
	}
}
