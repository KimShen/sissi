package com.sissi.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.addressing.Addressing;
import com.sissi.commons.IOUtils;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.impl.OnlineContextParam;
import com.sissi.feed.FeederBuilder;
import com.sissi.looper.Looper;
import com.sissi.looper.LooperBuilder;
import com.sissi.pipeline.InputFinder;
import com.sissi.pipeline.OutputBuilder;
import com.sissi.read.Reader;
import com.sissi.server.ServerCloser;
import com.sissi.server.ServerTlsBuilder;

/**
 * @author kim 2013年12月1日
 */
public class PrivateServerHandlerBuilder {

	private final String CONTEXT_ATTR = "CONTEXT_ATTR";

	private final String CONNECTOR_ATTR = "CONNECTOR_ATTR";

	private final AttributeKey<JIDContext> CONTEXT = AttributeKey.valueOf(CONTEXT_ATTR);

	private final AttributeKey<Looper> CONNECTOR = AttributeKey.valueOf(CONNECTOR_ATTR);

	private final Log log = LogFactory.getLog(this.getClass());

	private final Reader reader;

	private final InputFinder finder;

	private final Addressing addressing;

	private final ServerCloser serverCloser;

	private final FeederBuilder feederBuilder;

	private final LooperBuilder looperBuilder;

	private final OutputBuilder outputBuilder;

	private final ServerTlsBuilder serverTlsContext;

	private final JIDContextBuilder jidContextBuilder;

	public PrivateServerHandlerBuilder(Reader reader, InputFinder finder, Addressing addressing, ServerCloser serverCloser, FeederBuilder feederBuilder, LooperBuilder looperBuilder, OutputBuilder outputBuilder, ServerTlsBuilder serverTlsContext, JIDContextBuilder jidContextBuilder) {
		super();
		this.reader = reader;
		this.finder = finder;
		this.addressing = addressing;
		this.serverCloser = serverCloser;
		this.feederBuilder = feederBuilder;
		this.looperBuilder = looperBuilder;
		this.outputBuilder = outputBuilder;
		this.serverTlsContext = serverTlsContext;
		this.jidContextBuilder = jidContextBuilder;
	}

	public ChannelHandler build() throws IOException {
		return new PrivateServerHandler();
	}

	private class PrivateServerHandler extends ChannelInboundHandlerAdapter {

		private final PipedInputStream inPipe = new PipedInputStream();

		private final InputStream input = new BufferedInputStream(inPipe);

		private final OutputStream output = new BufferedOutputStream(new PipedOutputStream(inPipe));

		public PrivateServerHandler() throws IOException {
			super();
		}

		public void channelRegistered(final ChannelHandlerContext ctx) {
			this.createContext(ctx);
			this.createLooper(ctx);
		}

		public void channelUnregistered(ChannelHandlerContext ctx) {
			JIDContext context = ctx.attr(CONTEXT).get();
			if (context.isBinding()) {
				PrivateServerHandlerBuilder.this.serverCloser.close(context);
				PrivateServerHandlerBuilder.this.addressing.leave(context);
			}
			ctx.attr(CONNECTOR).get().stop();
			IOUtils.closeQuietly(this.input);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			try {
				this.output.write(this.copyToBytes(this.logIfNecessary(ctx, (ByteBuf) msg)));
				this.output.flush();
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

		private void createLooper(final ChannelHandlerContext ctx) {
			try {
				Looper looper = PrivateServerHandlerBuilder.this.looperBuilder.build(PrivateServerHandlerBuilder.this.reader.future(this.input), PrivateServerHandlerBuilder.this.feederBuilder.build(ctx.attr(CONTEXT).get(), PrivateServerHandlerBuilder.this.finder));
				ctx.attr(CONNECTOR).set(looper);
				looper.start();
			} catch (IOException e) {
				PrivateServerHandlerBuilder.this.log.error(e);
				throw new RuntimeException(e);
			}
		}

		private void createContext(final ChannelHandlerContext ctx) {
			NetworkTls networkTLS = new NetworkTls(PrivateServerHandlerBuilder.this.serverTlsContext, ctx);
			ctx.attr(CONTEXT).set(PrivateServerHandlerBuilder.this.jidContextBuilder.build(null, new OnlineContextParam(PrivateServerHandlerBuilder.this.outputBuilder.build(new NetworkTransfer(networkTLS, ctx)), networkTLS)));
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
			if (PrivateServerHandlerBuilder.this.log.isInfoEnabled()) {
				StringWriter trace = new StringWriter();
				cause.printStackTrace(new PrintWriter(trace));
				PrivateServerHandlerBuilder.this.log.info(trace.toString());
			}
		}
	}
}
