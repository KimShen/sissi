package com.sissi.netty.io;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Kim.shen 2013-10-16
 */
public class ByteBufferOutputStream extends OutputStream {

	private ByteBuf buffer;

	public ByteBufferOutputStream(ByteBuf buffer) {
		super();
		this.buffer = buffer;
	}

	@Override
	public void write(int b) throws IOException {
		buffer.writeByte(b);
	}

	public ByteBuf getBuffer() {
		return buffer;
	}
}
