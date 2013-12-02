package com.sissi.pipeline;

import com.sissi.context.JIDContext;
<<<<<<< HEAD
import com.sissi.protocol.Element;
import com.sissi.write.Writer.Transfer;
=======
import com.sissi.protocol.Node;
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4

/**
 * @author kim 2013-11-15
 */
public interface Output {

<<<<<<< HEAD
	public Boolean output(JIDContext context, Element node);
=======
	public Boolean output(JIDContext context, Node node);
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4

	public void close();

	public interface OutputBuilder {

		public Output build(Transfer transfer);
	}
}
