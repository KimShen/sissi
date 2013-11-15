package com.sissi.pipeline.output;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.netty.io.ByteBufferOutputStream;
import com.sissi.netty.listener.FailLogGenericFutureListener;
import com.sissi.pipeline.OutputPipeline;
import com.sissi.protocol.Protocol;
import com.sissi.write.Writer;

/**
 * @author kim 2013-10-31
 */
public class NetworkOutputPipeline implements OutputPipeline {

	private final static Log LOG = LogFactory.getLog(NetworkOutputPipeline.class);

	private Writer writer;

	private ChannelHandlerContext context;

	public NetworkOutputPipeline(Writer writer, ChannelHandlerContext context) {
		super();
		this.writer = writer;
		this.context = context;
	}

	@Override
	public boolean write(Protocol protocol) {
		try {
			ByteBuf byteBuffer = this.allocBuffer();
			this.writer.write(protocol, new ByteBufferOutputStream(byteBuffer));
			this.context.writeAndFlush(byteBuffer).addListener(FailLogGenericFutureListener.INSTANCE);
		} catch (IOException e) {
			LOG.error(e);
		}
		return false;
	}

	private ByteBuf allocBuffer() {
		ByteBuf byteBuf = this.context.alloc().buffer();
		LOG.debug("ByteBuf capacity:" + byteBuf.capacity());
		return byteBuf;
	}
}
