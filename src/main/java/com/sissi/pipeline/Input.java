package com.sissi.pipeline;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Protocol;

/**
 * @author kim 2013-10-24
 */
public interface Input {

	public boolean input(JIDContext context, Protocol protocol);
}
