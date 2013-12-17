package com.sissi.pipeline.in.starttls;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Input;
import com.sissi.protocol.Protocol;
import com.sissi.protocol.starttls.Proceed;

/**
 * @author kim 2013年12月17日
 */
public class StarttlsProcessor implements Input {

	@Override
	public Boolean input(JIDContext context, Protocol protocol) {
		context.write(Proceed.PROCEED);
		return true;
	}
}
