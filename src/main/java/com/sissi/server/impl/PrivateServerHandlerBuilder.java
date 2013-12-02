package com.sissi.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.addressing.Addressing;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContext.JIDContextBuilder;
import com.sissi.context.impl.OnlineContextBuilder.UserContextParam;
import com.sissi.feed.Feeder.FeederBuilder;
import com.sissi.looper.Looper;
import com.sissi.looper.Looper.LooperBuilder;
import com.sissi.pipeline.Input.InputFinder;
import com.sissi.pipeline.Output.OutputBuilder;
import com.sissi.read.Reader;
import com.sissi.server.ServerCloser;

/**
 * @author kim 2013年12月1日
 */
public class PrivateServerHandlerBuilder {

	private final static String CONTEXT_ATTR = "CONTEXT_ATTR";

	private final static String CONNECTOR_ATTR = "CONNECTOR_ATTR";

	private final static AttributeKey<JIDContext> CONTEXT = new AttributeKey<JIDContext>(CONTEXT_ATTR);

	private final static AttributeKey<Looper> CONNECTOR = new AttributeKey<Looper>(CONNECTOR_ATTR);

	private final Log log = LogFactory.getLog(this.getClass());

	private final Reader reader;

	private final InputFinder finder;

	private final Addressing addressing;

	private final ServerCloser serverCloser;

	private final FeederBuilder feederBuilder;

	private final LooperBuilder looperBuilder;

	private final OutputBuilder outputBuilder;

	private final JIDContextBuilder jidContextBuilder;

	public PrivateServerHandlerBuilder(Reader reader, InputFinder finder, Addressing addressing, ServerCloser serverCloser, FeederBuilder feederBuilder, LooperBuilder looperBuilder, OutputBuilder outputBuilder, JIDContextBuilder jidContextBuilder) {
		super();
		this.reader = reader;
		this.finder = finder;
		this.addressing = addressing;
		this.serverCloser = serverCloser;
		this.feederBuilder = feederBuilder;
		this.looperBuilder = looperBuilder;
		this.outputBuilder = outputBuilder;
		this.jidContextBuilder = jidContextBuilder;
	}

	public PrivateServerHandler build() throws IOException {
		return new PrivateServerHandler();
	}

	private class PrivateServerHandler extends ChannelInboundHandlerAdapter {

		private final PipedInputStream input = new PipedInputStream();

		private final PipedOutputStream output = new PipedOutputStream(input);

		public PrivateServerHandler() throws IOException {
			super();
		}

		public void channelActive(final ChannelHandlerContext ctx) {
			this.createContextAndJoinGroup(ctx);
			this.createLooperAndStart(ctx);
		}

		public void channelInactive(ChannelHandlerContext ctx) {
			ctx.attr(CONNECTOR).get().stop();
			if (ctx.attr(CONTEXT).get().isBinding()) {
				PrivateServerHandlerBuilder.this.serverCloser.close(ctx.attr(CONTEXT).get());
				PrivateServerHandlerBuilder.this.addressing.leave(ctx.attr(CONTEXT).get());
			}
			IOUtils.closeQuietly(this.input);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			try {
				this.output.write(this.copyToBytes(this.logIfNecessary(ctx, (ByteBuf) msg)));
			} catch (Exception e) {
				PrivateServerHandlerBuilder.this.log.fatal(e);
			} finally {
				ReferenceCountUtil.release(msg);
			}
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			this.logIfDetail(cause);
			if (ctx.channel().isOpen()) {
				ctx.close();
			}
		}

		private void createLooperAndStart(final ChannelHandlerContext ctx) {
			try {
				Looper looper = PrivateServerHandlerBuilder.this.looperBuilder.build(PrivateServerHandlerBuilder.this.reader.future(input), PrivateServerHandlerBuilder.this.feederBuilder.build(ctx.attr(CONTEXT).get(), PrivateServerHandlerBuilder.this.finder));
				ctx.attr(CONNECTOR).set(looper);
				looper.start();
			} catch (IOException e) {
				PrivateServerHandlerBuilder.this.log.error(e);
				throw new RuntimeException(e);
			}
		}

		private void createContextAndJoinGroup(final ChannelHandlerContext ctx) {
			ctx.attr(CONTEXT).set(PrivateServerHandlerBuilder.this.jidContextBuilder.build(null, new UserContextParam(PrivateServerHandlerBuilder.this.outputBuilder.build(new NetworkTransfer(ctx)))));
		}

		private byte[] copyToBytes(ByteBuf byteBuf) {
			byte[] data = new byte[byteBuf.readableBytes()];
			byteBuf.readBytes(data);
			return data;
		}

		private ByteBuf logIfNecessary(ChannelHandlerContext ctx, ByteBuf byteBuf) {
			if (PrivateServerHandlerBuilder.this.log.isInfoEnabled()) {
				JIDContext context = ctx.attr(CONTEXT).get();
				PrivateServerHandlerBuilder.this.log.info("Read on " + (context != null && context.getJid() != null ? context.getJid().asString() : "N/A") + ": " + byteBuf.toString(Charset.forName("UTF-8")));
				byteBuf.readerIndex(0);
			}
			return byteBuf;
		}

		private void logIfDetail(Throwable cause) {
			if (PrivateServerHandlerBuilder.this.log.isWarnEnabled()) {
				StringWriter trace = new StringWriter();
				cause.printStackTrace(new PrintWriter(trace));
				PrivateServerHandlerBuilder.this.log.warn(trace.toString());
			}
		}
	}
}
