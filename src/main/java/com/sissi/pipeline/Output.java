package com.sissi.pipeline;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-11-15
 */
public interface Output {

	public Boolean output(JIDContext context, Element element);

	public Output close();
}
