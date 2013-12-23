package com.sissi.write;

import io.netty.buffer.ByteBuf;

/**
 * @author kim 2013年12月23日
 */
public interface Transfer {

	public ByteBuf allocBuffer();

	public Transfer transfer(ByteBuf bytebuf);

	public Transfer close();
}