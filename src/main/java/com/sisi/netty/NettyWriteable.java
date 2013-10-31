package com.sisi.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.context.Context.Writeable;
import com.sisi.netty.io.ByteBufferOutputStream;
import com.sisi.netty.listener.FailLogGenericFutureListener;
import com.sisi.protocol.Protocol;
import com.sisi.write.Writer;

/**
 * @author kim 2013-10-31
 */
public class NettyWriteable implements Writeable {

	private final static Log LOG = LogFactory.getLog(NettyWriteable.class);

	private Writer writer;

	private ChannelHandlerContext context;

	public NettyWriteable(Writer writer, ChannelHandlerContext context) {
		super();
		this.writer = writer;
		this.context = context;
	}

	@Override
	public void writeAndFlush(Protocol protocol) {
		try {
			ByteBuf byteBuffer = this.allocBuffer();
			this.writer.write(protocol, new ByteBufferOutputStream(byteBuffer));
			this.context.writeAndFlush(byteBuffer).addListener(FailLogGenericFutureListener.INSTANCE);
		} catch (IOException e) {
			LOG.error(e);
		}
	}

	private ByteBuf allocBuffer() {
		ByteBuf byteBuf = this.context.alloc().buffer();
		LOG.debug("ByteBuf capacity " + byteBuf.capacity());
		return byteBuf;
	}
}
