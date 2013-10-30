package com.sisi.netty.io;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Kim.shen 2013-10-16
 */
public class ByteBufferInputStream extends InputStream {

	private ByteBuf buffer;

	public ByteBufferInputStream(ByteBuf buffer) {
		super();
		this.buffer = buffer;
	}

	@Override
	public int read() throws IOException {
		return this.buffer.readableBytes() > 0 ? buffer.readByte() : -1;
	}
}
