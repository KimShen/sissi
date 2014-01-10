package com.sissi.pipeline;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Element;

/**
 * Process output protocol
 * 
 * @author kim 2013-11-15
 */
public interface Output {

	public Boolean output(JIDContext context, Element element);

	/**
	 * Close output
	 * @return
	 */
	public Output close();
}
