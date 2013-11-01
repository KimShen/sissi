package com.sisi.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sisi.connector.Connector;
import com.sisi.connector.ConnectorBuilder;
import com.sisi.context.Context;
import com.sisi.context.user.UserContext;
import com.sisi.feed.FeederBuilder;
import com.sisi.group.Group;
import com.sisi.process.Processor;
import com.sisi.read.Reader;
import com.sisi.write.Writer;

/**
 * @author kim 2013-10-26
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

	private final static Log LOG = LogFactory.getLog(ServerHandler.class);

	private final static AttributeKey<Context> CONTEXT = new AttributeKey<Context>("CONTEXT");

	private final static AttributeKey<Connector> CONNECTOR = new AttributeKey<Connector>("CONNECTOR");

	private final PipedInputStream input = new PipedInputStream();

	private final PipedOutputStream output;

	private final Group group;

	private final Writer writer;

	private final Reader reader;

	private final List<Processor> processors;

	private final FeederBuilder feederBuilder;

	private final ConnectorBuilder connectorBuilder;

	public ServerHandler(Group group, Reader reader, Writer writer, List<Processor> processors, FeederBuilder feederBuilder, ConnectorBuilder connectorBuilder) throws IOException {
		super();
		this.output = new PipedOutputStream(input);
		this.group = group;
		this.reader = reader;
		this.writer = writer;
		this.processors = processors;
		this.feederBuilder = feederBuilder;
		this.connectorBuilder = connectorBuilder;
	}

	public void channelActive(final ChannelHandlerContext ctx) {
		this.createContextAndJoinGroup(ctx);
		try {
			Connector connector = this.connectorBuilder.builder(this.reader.future(input), this.feederBuilder.builder(ctx.attr(CONTEXT).get(), this.processors));
			ctx.attr(CONNECTOR).set(connector);
			connector.start();
			LOG.debug("Connector start ... ");
		} catch (IOException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	private void createContextAndJoinGroup(final ChannelHandlerContext ctx) {
		ctx.attr(CONTEXT).set(new UserContext(new NettyWriteable(this.writer, ctx)));
		this.group.add(ctx.attr(CONTEXT).get());
	}

	public void channelInactive(ChannelHandlerContext ctx) {
		this.group.remove(ctx.attr(CONTEXT).get());
		ctx.attr(CONNECTOR).get().stop();
		IOUtils.closeQuietly(this.input);
		IOUtils.closeQuietly(this.output);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			ByteBuf byteBuf = (ByteBuf) msg;
			this.logIfNecessary(byteBuf);
			this.output.write(this.copyToBytes(byteBuf));
		} catch (Exception e) {
			LOG.fatal(e);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	private byte[] copyToBytes(ByteBuf byteBuf) {
		byte[] data = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(data);
		return data;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		LOG.error(cause);
		ctx.close();
	}

	private void logIfNecessary(ByteBuf byteBuf) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Read: " + byteBuf.toString(Charset.forName("UTF-8")));
			byteBuf.readerIndex(0);
		}
	}
}