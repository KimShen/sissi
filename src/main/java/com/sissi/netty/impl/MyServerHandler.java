package com.sissi.netty.impl;

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
import com.sissi.context.JIDContextBuilder;
import com.sissi.context.impl.UserContextParam;
import com.sissi.feed.FeederBuilder;
import com.sissi.looper.Looper;
import com.sissi.looper.LooperBuilder;
import com.sissi.netty.ServerCloser;
import com.sissi.pipeline.InputCondition.InputFinder;
import com.sissi.pipeline.out.NetworkProcessor;
import com.sissi.read.Reader;
import com.sissi.write.Writer;

/**
 * @author kim 2013-10-26
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

	private final static Log LOG = LogFactory.getLog(MyServerHandler.class);

	private final static String CONTEXT_ATTR = "CONTEXT_ATTR";

	private final static String CONNECTOR_ATTR = "CONNECTOR_ATTR";

	private final static AttributeKey<JIDContext> CONTEXT = new AttributeKey<JIDContext>(CONTEXT_ATTR);

	private final static AttributeKey<Looper> CONNECTOR = new AttributeKey<Looper>(CONNECTOR_ATTR);

	private final PipedInputStream input = new PipedInputStream();

	private final PipedOutputStream output = new PipedOutputStream(input);

	private final Writer writer;

	private final Reader reader;

	private final InputFinder finder;

	private final Addressing addressing;

	private final ServerCloser serverCloser;

	private final FeederBuilder feederBuilder;

	private final LooperBuilder looperBuilder;

	private final JIDContextBuilder jidContextBuilder;

	public MyServerHandler(Reader reader, Writer writer, InputFinder finder, Addressing addressing, ServerCloser serverCloser, FeederBuilder feederBuilder, LooperBuilder looperBuilder, JIDContextBuilder jidContextBuilder) throws IOException {
		super();
		this.reader = reader;
		this.writer = writer;
		this.finder = finder;
		this.addressing = addressing;
		this.serverCloser = serverCloser;
		this.feederBuilder = feederBuilder;
		this.looperBuilder = looperBuilder;
		this.jidContextBuilder = jidContextBuilder;
	}

	public void channelActive(final ChannelHandlerContext ctx) {
		this.createContextAndJoinGroup(ctx);
		this.createLooperAndStart(ctx);
	}

	public void channelUnregistered(ChannelHandlerContext ctx) {
		this.serverCloser.callback(ctx.attr(CONTEXT).get());
		this.addressing.leave(ctx.attr(CONTEXT).get());
		ctx.attr(CONNECTOR).get().stop();
		IOUtils.closeQuietly(this.input);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			ByteBuf byteBuf = (ByteBuf) msg;
			this.logIfNecessary(ctx, byteBuf);
			this.output.write(this.copyToBytes(byteBuf));
		} catch (Exception e) {
			LOG.fatal(e);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		this.logIfDetail(cause);
		ctx.close();
	}

	private void logIfDetail(Throwable cause) {
		StringWriter trace = new StringWriter();
		cause.printStackTrace(new PrintWriter(trace));
		LOG.error(trace.toString());
	}

	private void createLooperAndStart(final ChannelHandlerContext ctx) {
		try {
			Looper looper = this.looperBuilder.build(this.reader.future(input), this.feederBuilder.build(ctx.attr(CONTEXT).get(), this.finder));
			ctx.attr(CONNECTOR).set(looper);
			looper.start();
		} catch (IOException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	private void createContextAndJoinGroup(final ChannelHandlerContext ctx) {
		NetworkProcessor network = new NetworkProcessor(this.writer, ctx);
		ctx.attr(CONTEXT).set(this.jidContextBuilder.build(null, new UserContextParam(network)));
	}

	private byte[] copyToBytes(ByteBuf byteBuf) {
		byte[] data = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(data);
		return data;
	}

	private void logIfNecessary(ChannelHandlerContext ctx, ByteBuf byteBuf) {
		if (LOG.isInfoEnabled()) {
			JIDContext context = ctx.attr(CONTEXT).get();
			LOG.info("Read on " + (context != null && context.getJid() != null ? context.getJid().asString() : "N/A") + ": " + byteBuf.toString(Charset.forName("UTF-8")));
			byteBuf.readerIndex(0);
		}
	}
}