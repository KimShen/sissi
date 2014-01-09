package com.sissi.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.addressing.Addressing;
import com.sissi.commons.apache.IOUtils;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.context.impl.OnlineContextBuilder;
import com.sissi.feed.FeederBuilder;
import com.sissi.looper.Looper;
import com.sissi.looper.LooperBuilder;
import com.sissi.pipeline.InputFinder;
import com.sissi.pipeline.Output;
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

		private final PipedOutputStream outPipe = new PipedOutputStream(inPipe);

		private final BufferedInputStream input = new BufferedInputStream(inPipe);

		private final BufferedOutputStream output = new BufferedOutputStream(outPipe);

		public PrivateServerHandler() throws IOException {
			super();
		}

		public void channelRegistered(final ChannelHandlerContext ctx) {
			this.createContext(ctx);
			this.createLooper(ctx);
		}

		public void channelUnregistered(ChannelHandlerContext ctx) {
			try {
				JIDContext context = ctx.attr(CONTEXT).get();
				if (context.isBinding()) {
					PrivateServerHandlerBuilder.this.serverCloser.close(context);
					PrivateServerHandlerBuilder.this.addressing.leave(context);
				}
				ctx.attr(CONNECTOR).get().stop();
				this.closeParser();
			} catch (Exception e) {
				if (PrivateServerHandlerBuilder.this.log.isErrorEnabled()) {
					PrivateServerHandlerBuilder.this.log.error(e.toString());
					e.printStackTrace();
				}
			}
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) {
			try {
				this.output.write(this.copyToBytes(this.logIfNecessary(ctx, (ByteBuf) msg)));
				this.output.flush();
			} catch (Exception e) {
				PrivateServerHandlerBuilder.this.log.fatal(e.toString());
			} finally {
				ReferenceCountUtil.release(msg);
			}
		}

		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			this.ping(ctx, evt);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			this.logIfDetail(cause);
			ctx.close();
		}
		
		private void ping(ChannelHandlerContext ctx, Object evt) {
			if (evt.getClass().isAssignableFrom(IdleStateEvent.class) && IdleStateEvent.class.cast(evt).state() == IdleState.READER_IDLE) {
				ctx.attr(CONTEXT).get().ping();
			}
		}

		private void closeParser() throws IOException {
			this.output.write(-1);
			this.output.flush();
			IOUtils.closeQuietly(this.output);
			IOUtils.closeQuietly(this.outPipe);
		}

		private void createLooper(final ChannelHandlerContext ctx) {
			try {
				Looper looper = PrivateServerHandlerBuilder.this.looperBuilder.build(PrivateServerHandlerBuilder.this.reader.future(this.input), PrivateServerHandlerBuilder.this.feederBuilder.build(ctx.attr(CONTEXT).get(), PrivateServerHandlerBuilder.this.finder));
				ctx.attr(CONNECTOR).set(looper);
				looper.start();
			} catch (IOException e) {
				PrivateServerHandlerBuilder.this.log.error(e.toString());
				throw new RuntimeException(e);
			}
		}

		private void createContext(final ChannelHandlerContext ctx) {
			ctx.attr(CONTEXT).set(PrivateServerHandlerBuilder.this.jidContextBuilder.build(null, new NettyProxyContextParam(ctx)));
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

	private class NettyProxyContextParam implements JIDContextParam {

		private final ChannelHandlerContext ctx;

		private final FixDomainServerTls serverTls;

		private final Output output;

		public NettyProxyContextParam(ChannelHandlerContext ctx) {
			super();
			this.ctx = ctx;
			this.serverTls = new FixDomainServerTls(PrivateServerHandlerBuilder.this.serverTlsContext, ctx);
			this.output = PrivateServerHandlerBuilder.this.outputBuilder.build(new NetworkTransfer(this.serverTls, ctx));
		}

		@Override
		public <T> T find(String key, Class<T> clazz) {
			switch (key) {
			case OnlineContextBuilder.KEY_OUTPUT:
				return clazz.cast(this.output);
			case OnlineContextBuilder.KEY_SERVERTLS:
				return clazz.cast(this.serverTls);
			case OnlineContextBuilder.KEY_ADDRESS:
				return clazz.cast(this.ctx.channel().remoteAddress());
			}
			return null;
		}
	}
}
