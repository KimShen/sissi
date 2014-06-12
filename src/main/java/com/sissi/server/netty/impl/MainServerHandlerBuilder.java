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
import com.sissi.commons.apache.IOUtil;
import com.sissi.context.JIDContext;
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.JIDContextParam;
import com.sissi.feed.FeederBuilder;
import com.sissi.io.read.Reader;
import com.sissi.looper.Looper;
import com.sissi.looper.LooperBuilder;
import com.sissi.pipeline.InputFinder;
import com.sissi.pipeline.Output;
import com.sissi.pipeline.OutputBuilder;
import com.sissi.resource.ResourceCounter;
import com.sissi.server.getout.Getout;
import com.sissi.server.netty.ChannelHandlerBuilder;
import com.sissi.server.tls.SSLContextBuilder;
import com.sissi.server.tls.impl.FixDomainStartTls;

/**
 * @author kim 2013年12月1日
 */
public class MainServerHandlerBuilder implements ChannelHandlerBuilder {

	private final AttributeKey<JIDContext> attrContext = AttributeKey.valueOf("ATTR_CONTEXT");

	private final AttributeKey<Looper> attrConnector = AttributeKey.valueOf("CONNECTOR_ATTR");

	private final String resource = PersonalServerHandler.class.getSimpleName();

	private final Log log = LogFactory.getLog(this.getClass());

	private final Reader reader;

	private final Getout getout;

	private final InputFinder finder;

	private final Addressing addressing;

	private final FeederBuilder feederBuilder;

	private final LooperBuilder looperBuilder;

	private final OutputBuilder outputBuilder;

	private final ResourceCounter resourceCounter;

	private final SSLContextBuilder sslContextBuilder;

	private final JIDContextBuilder jidContextBuilder;

	public MainServerHandlerBuilder(Reader reader, Getout getout, InputFinder finder, Addressing addressing, FeederBuilder feederBuilder, LooperBuilder looperBuilder, OutputBuilder outputBuilder, ResourceCounter resourceCounter, SSLContextBuilder sslContextBuilder, JIDContextBuilder jidContextBuilder) {
		super();
		this.reader = reader;
		this.finder = finder;
		this.getout = getout;
		this.addressing = addressing;
		this.feederBuilder = feederBuilder;
		this.looperBuilder = looperBuilder;
		this.outputBuilder = outputBuilder;
		this.resourceCounter = resourceCounter;
		this.sslContextBuilder = sslContextBuilder;
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
			MainServerHandlerBuilder.this.resourceCounter.increment(MainServerHandlerBuilder.this.resource);
		}

		public void channelUnregistered(ChannelHandlerContext ctx) throws IOException {
			this.closeContext(ctx).closeLooper(ctx);
			MainServerHandlerBuilder.this.resourceCounter.decrement(MainServerHandlerBuilder.this.resource);
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
				ctx.attr(MainServerHandlerBuilder.this.attrContext).get().ping();
			}
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			MainServerHandlerBuilder.this.log.error(cause.toString());
			Trace.trace(MainServerHandlerBuilder.this.log, cause);
			ctx.close();
		}

		private PersonalServerHandler closeLooper(ChannelHandlerContext ctx) {
			ctx.attr(MainServerHandlerBuilder.this.attrConnector).get().stop();
			return this;
		}

		private PersonalServerHandler closeContext(ChannelHandlerContext ctx) throws IOException {
			JIDContext context = ctx.attr(MainServerHandlerBuilder.this.attrContext).get();
			MainServerHandlerBuilder.this.getout.getout(context);
			MainServerHandlerBuilder.this.addressing.leave(context);
			this.closeParser();
			return this;
		}

		private PersonalServerHandler closeParser() throws IOException {
			this.output.write(-1);
			this.output.flush();
			IOUtil.closeQuietly(this.output);
			IOUtil.closeQuietly(this.outPipe);
			return this;
		}

		private PersonalServerHandler createLooper(final ChannelHandlerContext ctx) throws IOException {
			Looper looper = MainServerHandlerBuilder.this.looperBuilder.build(MainServerHandlerBuilder.this.reader.future(this.input), MainServerHandlerBuilder.this.feederBuilder.build(ctx.attr(MainServerHandlerBuilder.this.attrContext).get(), MainServerHandlerBuilder.this.finder));
			ctx.attr(MainServerHandlerBuilder.this.attrConnector).set(looper);
			looper.start();
			return this;
		}

		private PersonalServerHandler createContext(final ChannelHandlerContext ctx) {
			ctx.attr(MainServerHandlerBuilder.this.attrContext).set(MainServerHandlerBuilder.this.jidContextBuilder.build(null, new NettyProxyContextParam(ctx)));
			return this;
		}

		private OutputStream copyToBytes(OutputStream output, ByteBuf byteBuf) throws IOException {
			byteBuf.readBytes(output, byteBuf.readableBytes());
			return output;
		}

		private ByteBuf logIfNecessary(ChannelHandlerContext ctx, ByteBuf byteBuf) {
			if (MainServerHandlerBuilder.this.log.isInfoEnabled()) {
				JIDContext context = ctx.attr(MainServerHandlerBuilder.this.attrContext).get();
				MainServerHandlerBuilder.this.log.info("Read on " + (context != null && context.jid() != null ? context.jid().asString() : "N/A") + ": " + byteBuf.toString(Charset.forName("UTF-8")));
				byteBuf.readerIndex(0);
			}
			return byteBuf;
		}
	}

	private class NettyProxyContextParam implements JIDContextParam {

		private final FixDomainStartTls startTls;

		private final ChannelHandlerContext ctx;

		private final Output output;

		public NettyProxyContextParam(ChannelHandlerContext ctx) {
			super();
			this.ctx = ctx;
			this.startTls = new FixDomainStartTls(MainServerHandlerBuilder.this.sslContextBuilder, ctx);
			this.output = MainServerHandlerBuilder.this.outputBuilder.build(new NetworkTransfer(this.startTls, ctx));
		}

		@Override
		public <T> T find(String key, Class<T> clazz) {
			switch (key) {
			case JIDContextParam.KEY_OUTPUT:
				return clazz.cast(this.output);
			case JIDContextParam.KEY_STARTTLS:
				return clazz.cast(this.startTls);
			case JIDContextParam.KEY_ADDRESS:
				return clazz.cast(this.ctx.channel().remoteAddress());
			}
			return null;
		}
	}
}
