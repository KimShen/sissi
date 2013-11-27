package com.sissi.pipeline;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Node;

/**
 * @author kim 2013-11-15
 */
public interface Output {

	public Boolean output(JIDContext context, Node node);

	public void close();
}
