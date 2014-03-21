package com.sissi.server.netty.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.addressing.Addressing;
import com.sissi.commons.Trace;
import com.sissi.commons.apache.IOUtils;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.feed.FeederBuilder;
import com.sissi.looper.Looper;
import com.sissi.looper.LooperBuilder;
import com.sissi.pipeline.InputFinder;
import com.sissi.pipeline.Output;
import com.sissi.pipeline.OutputBuilder;
import com.sissi.read.Reader;
import com.sissi.resource.ResourceCounter;
import com.sissi.server.ServerCloser;
import com.sissi.server.impl.NetworkTransfer;
import com.sissi.server.netty.ServerHandlerBuilder;
import com.sissi.server.tls.ServerTlsBuilder;
import com.sissi.server.tls.impl.FixDomainServerTls;

/**
 * @author kim 2013年12月1日
 */
public class PersonalServerHandlerBuilder implements ServerHandlerBuilder {

	private final AttributeKey<JIDContext> attrContext = AttributeKey.valueOf("ATTR_CONTEXT");

	private final AttributeKey<Looper> attrConnector = AttributeKey.valueOf("CONNECTOR_ATTR");

	private final String resource = PersonalServerHandler.class.getSimpleName();

	private final Log log = LogFactory.getLog(this.getClass());

	private final Reader reader;

	private final InputFinder finder;

	private final Addressing addressing;

	private final ServerCloser serverCloser;

	private final FeederBuilder feederBuilder;

	private final LooperBuilder looperBuilder;

	private final OutputBuilder outputBuilder;

	private final ResourceCounter resourceCounter;

	private final ServerTlsBuilder serverTlsContext;

	private final JIDContextBuilder jidContextBuilder;

	public PersonalServerHandlerBuilder(Reader reader, InputFinder finder, Addressing addressing, ServerCloser serverCloser, FeederBuilder feederBuilder, LooperBuilder looperBuilder, OutputBuilder outputBuilder, ResourceCounter resourceCounter, ServerTlsBuilder serverTlsContext, JIDContextBuilder jidContextBuilder) {
		super();
		this.reader = reader;
		this.finder = finder;
		this.addressing = addressing;
		this.serverCloser = serverCloser;
		this.feederBuilder = feederBuilder;
		this.looperBuilder = looperBuilder;
		this.outputBuilder = outputBuilder;
		this.resourceCounter = resourceCounter;
		this.serverTlsContext = serverTlsContext;
		this.jidContextBuilder = jidContextBuilder;
	}

	public ChannelHandler build() throws IOException {
		return new PersonalServerHandler();
	}

	private class PersonalServerHandler extends ChannelInboundHandlerAdapter {

		private final PipedInputStream inPipe = new PipedInputStream();

		private final PipedOutputStream outPipe = new PipedOutputStream(inPipe);

		private final BufferedInputStream input = new BufferedInputStream(inPipe);

		private final BufferedOutputStream output = new BufferedOutputStream(outPipe);

		public PersonalServerHandler() throws IOException {
			super();
		}

		public void channelRegistered(final ChannelHandlerContext ctx) throws IOException {
			this.createContext(ctx).createLooper(ctx);
			PersonalServerHandlerBuilder.this.resourceCounter.increment(PersonalServerHandlerBuilder.this.resource);
		}

		public void channelUnregistered(ChannelHandlerContext ctx) throws IOException {
			this.closeContext(ctx).closeLooper(ctx);
			PersonalServerHandlerBuilder.this.resourceCounter.decrement(PersonalServerHandlerBuilder.this.resource);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {
			try {
				this.copyToBytes(this.output, this.logIfNecessary(ctx, ByteBuf.class.cast(msg))).flush();
			} finally {
				ReferenceCountUtil.release(msg);
			}
		}

		public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
			if (evt.getClass() == IdleStateEvent.class) {
				ctx.attr(PersonalServerHandlerBuilder.this.attrContext).get().ping();
			}
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			PersonalServerHandlerBuilder.this.log.error(cause.toString());
			Trace.trace(PersonalServerHandlerBuilder.this.log, cause);
			ctx.close();
		}

		private PersonalServerHandler closeLooper(ChannelHandlerContext ctx) {
			ctx.attr(PersonalServerHandlerBuilder.this.attrConnector).get().stop();
			return this;
		}

		private PersonalServerHandler closeContext(ChannelHandlerContext ctx) throws IOException {
			JIDContext context = ctx.attr(PersonalServerHandlerBuilder.this.attrContext).get();
			if (context.binding()) {
				PersonalServerHandlerBuilder.this.serverCloser.close(context);
				PersonalServerHandlerBuilder.this.addressing.leave(context);
			}
			this.closeParser();
			return this;
		}

		private PersonalServerHandler closeParser() throws IOException {
			this.output.write(-1);
			this.output.flush();
			IOUtils.closeQuietly(this.output);
			IOUtils.closeQuietly(this.outPipe);
			return this;
		}

		private PersonalServerHandler createLooper(final ChannelHandlerContext ctx) throws IOException {
			Looper looper = PersonalServerHandlerBuilder.this.looperBuilder.build(PersonalServerHandlerBuilder.this.reader.future(this.input), PersonalServerHandlerBuilder.this.feederBuilder.build(ctx.attr(PersonalServerHandlerBuilder.this.attrContext).get(), PersonalServerHandlerBuilder.this.finder));
			ctx.attr(PersonalServerHandlerBuilder.this.attrConnector).set(looper);
			looper.start();
			return this;
		}

		private PersonalServerHandler createContext(final ChannelHandlerContext ctx) {
			ctx.attr(PersonalServerHandlerBuilder.this.attrContext).set(PersonalServerHandlerBuilder.this.jidContextBuilder.build(null, new NettyProxyContextParam(ctx)));
			return this;
		}

		private OutputStream copyToBytes(OutputStream output, ByteBuf byteBuf) throws IOException {
			byteBuf.readBytes(output, byteBuf.readableBytes());
			return output;
		}

		private ByteBuf logIfNecessary(ChannelHandlerContext ctx, ByteBuf byteBuf) {
			if (PersonalServerHandlerBuilder.this.log.isInfoEnabled()) {
				JIDContext context = ctx.attr(PersonalServerHandlerBuilder.this.attrContext).get();
				PersonalServerHandlerBuilder.this.log.info("Read on " + (context != null && context.jid() != null ? context.jid().asString() : "N/A") + ": " + byteBuf.toString(Charset.forName("UTF-8")));
				byteBuf.readerIndex(0);
			}
			return byteBuf;
		}
	}

	private class NettyProxyContextParam implements JIDContextParam {

		private final FixDomainServerTls serverTls;

		private final ChannelHandlerContext ctx;

		private final Output output;

		public NettyProxyContextParam(ChannelHandlerContext ctx) {
			super();
			this.ctx = ctx;
			this.serverTls = new FixDomainServerTls(PersonalServerHandlerBuilder.this.serverTlsContext, ctx);
			this.output = PersonalServerHandlerBuilder.this.outputBuilder.build(new NetworkTransfer(this.serverTls, ctx));
		}

		@Override
		public <T> T find(String key, Class<T> clazz) {
			switch (key) {
			case JIDContextParam.KEY_OUTPUT:
				return clazz.cast(this.output);
			case JIDContextParam.KEY_SERVERTLS:
				return clazz.cast(this.serverTls);
			case JIDContextParam.KEY_ADDRESS:
				return clazz.cast(this.ctx.channel().remoteAddress());
			}
			return null;
		}
	}
}
