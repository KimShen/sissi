package com.sissi.netty;

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

import com.sissi.connector.Connector;
import com.sissi.connector.ConnectorBuilder;
import com.sissi.context.Context;
import com.sissi.context.user.UserContext;
import com.sissi.feed.FeederBuilder;
import com.sissi.process.ProcessorFinder;
import com.sissi.read.Reader;
import com.sissi.write.Writer;

/**
 * @author kim 2013-10-26
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

	private final static Log LOG = LogFactory.getLog(ServerHandler.class);

	private final static String CONTEXT_ATTR = "CONTEXT_ATTR";

	private final static String CONNECTOR_ATTR = "CONNECTOR_ATTR";

	private final static AttributeKey<Context> CONTEXT = new AttributeKey<Context>(CONTEXT_ATTR);

	private final static AttributeKey<Connector> CONNECTOR = new AttributeKey<Connector>(CONNECTOR_ATTR);

	private final PipedInputStream input = new PipedInputStream();

	private final PipedOutputStream output = new PipedOutputStream(input);

	private final Writer writer;

	private final Reader reader;

	private final ProcessorFinder finder;

	private final FeederBuilder feederBuilder;

	private final ConnectorBuilder connectorBuilder;

	public ServerHandler(Reader reader, Writer writer, ProcessorFinder finder, FeederBuilder feederBuilder, ConnectorBuilder connectorBuilder) throws IOException {
		super();
		this.reader = reader;
		this.writer = writer;
		this.finder = finder;
		this.feederBuilder = feederBuilder;
		this.connectorBuilder = connectorBuilder;
	}

	public void channelActive(final ChannelHandlerContext ctx) {
		this.createContextAndJoinGroup(ctx);
		this.createConnectorAndStart(ctx);
	}

	public void channelInactive(ChannelHandlerContext ctx) {
		ctx.attr(CONNECTOR).get().stop();
		IOUtils.closeQuietly(this.input);
		IOUtils.closeQuietly(this.output);
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

	private void createConnectorAndStart(final ChannelHandlerContext ctx) {
		try {
			Connector connector = this.connectorBuilder.builder(this.reader.future(input), this.feederBuilder.builder(ctx.attr(CONTEXT).get(), this.finder));
			connector.start();
			ctx.attr(CONNECTOR).set(connector);
		} catch (IOException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	private void createContextAndJoinGroup(final ChannelHandlerContext ctx) {
		ctx.attr(CONTEXT).set(new UserContext(new WriteableWrapper(this.writer, ctx)));
	}

	private byte[] copyToBytes(ByteBuf byteBuf) {
		byte[] data = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(data);
		return data;
	}

	private void logIfNecessary(ChannelHandlerContext ctx, ByteBuf byteBuf) {
		if (LOG.isInfoEnabled()) {
			Context context = ctx.attr(CONTEXT).get();
			LOG.info("Read on " + (context != null && context.jid() != null ? context.jid().asStringWithNaked() : "N/A") + ": " + byteBuf.toString(Charset.forName("UTF-8")));
			byteBuf.readerIndex(0);
		}
	}
}