package com.sissi.pipeline;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Element;
import com.sissi.write.Writer.Transfer;

/**
 * @author kim 2013-11-15
 */
public interface Output {

	public Boolean output(JIDContext context, Element node);

	public void close();

	public interface OutputBuilder {

		public Output build(Transfer writeable);
	}
}
