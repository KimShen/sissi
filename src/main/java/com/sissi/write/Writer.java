package com.sissi.write;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.OutputStream;

import com.sissi.context.JIDContext;
<<<<<<< HEAD
import com.sissi.protocol.Element;
=======
import com.sissi.protocol.Node;
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4

/**
 * @author kim 2013-10-24
 */
public interface Writer {

<<<<<<< HEAD
	public void write(JIDContext context, Element node, OutputStream outputStream) throws IOException;

	public interface Transfer {

		public ByteBuf allocBuffer();

		public void transfer(ByteBuf bytebuf);

		public void close();
	}
=======
	public void write(JIDContext context, Node node, OutputStream outputStream) throws IOException;
>>>>>>> bb8f10e305055ee0e7cfa0d6430d98b394218ce4
}
