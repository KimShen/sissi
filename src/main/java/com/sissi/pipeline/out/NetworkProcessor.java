package com.sissi.pipeline.out;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.pipeline.Output;
import com.sissi.protocol.Node;
import com.sissi.write.Writer;

/**
 * @author kim 2013-10-31
 */
public class NetworkProcessor implements Output {

	private final static Log LOG = LogFactory.getLog(NetworkProcessor.class);

	private Writer writer;

	private ChannelHandlerContext context;

	public NetworkProcessor(Writer writer, ChannelHandlerContext context) {
		super();
		this.writer = writer;
		this.context = context;
	}

	@Override
	public Boolean output(JIDContext context, Node node) {
		try {
			ByteBuf byteBuffer = this.allocBuffer();
			this.writer.write(context, node, new ByteBufferOutputStream(byteBuffer));
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

	@Override
	public void close() {
		this.context.close().addListener(FailLogGenericFutureListener.INSTANCE);
	}

	private static class FailLogGenericFutureListener implements GenericFutureListener<Future<Void>> {

		private final static GenericFutureListener<Future<Void>> INSTANCE = new FailLogGenericFutureListener();

		private Log log = LogFactory.getLog(this.getClass());

		private FailLogGenericFutureListener() {
			super();
		}

		public void operationComplete(Future<Void> future) throws Exception {
			if (!future.isSuccess()) {
				this.logIfDetail(future.cause());
			}
		}

		private void logIfDetail(Throwable cause) {
			StringWriter trace = new StringWriter();
			cause.printStackTrace(new PrintWriter(trace));
			this.log.error(trace.toString());
		}
	}

	private class ByteBufferOutputStream extends OutputStream {

		private ByteBuf buffer;

		public ByteBufferOutputStream(ByteBuf buffer) {
			super();
			this.buffer = buffer;
		}

		@Override
		public void write(int b) throws IOException {
			buffer.writeByte(b);
		}
	}
}
