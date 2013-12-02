package com.sissi.write;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.OutputStream;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Element;

/**
 * @author kim 2013-10-24
 */
public interface Writer {

	public void write(JIDContext context, Element node, OutputStream outputStream) throws IOException;

	public interface Transfer {

		public void transfer(ByteBuf bytebuf);

		public ByteBuf allocBuffer();

		public void close();
	}
}
